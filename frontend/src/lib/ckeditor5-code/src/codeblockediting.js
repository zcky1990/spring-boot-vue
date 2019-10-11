/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

/**
 * @module block-quote/blockquoteediting
 */

import Plugin from '@ckeditor/ckeditor5-core/src/plugin';

import CodeBlockCommand from './codeblockcommand';

/**
 * The block quote editing.
 *
 * Introduces the `'blockQuote'` command and the `'blockQuote'` model element.
 *
 * @extends module:core/plugin~Plugin
 */
export default class CodeBlockEditing extends Plugin {
	/**
	 * @inheritDoc
	 */
	init() {
		const editor = this.editor;
		const schema = editor.model.schema;

		editor.commands.add( 'pre', new CodeBlockCommand( editor ) );

		schema.register( 'pre', {
			allowWhere: '$block',
			allowContentOf: '$root'
		} );

		// Disallow blockQuote in blockQuote.
		schema.addChildCheck( ( ctx, childDef ) => {
			if ( ctx.endsWith( 'pre' ) && childDef.name == 'pre' ) {
				return false;
			}
		} );

		editor.conversion.elementToElement( { model: 'pre', view: 'pre' } );

		// Postfixer which cleans incorrect model states connected with block quotes.
		editor.model.document.registerPostFixer( writer => {
			const changes = editor.model.document.differ.getChanges();

			for ( const entry of changes ) {
				if ( entry.type == 'insert' ) {
					const element = entry.position.nodeAfter;

					if ( !element ) {
						// We are inside a text node.
						continue;
					}

					if ( element.is( 'pre' ) && element.isEmpty ) {
						// Added an empty blockQuote - remove it.
						writer.remove( element );

						return true;
					} else if ( element.is( 'pre' ) && !schema.checkChild( entry.position, element ) ) {
						// Added a blockQuote in incorrect place - most likely inside another blockQuote. Unwrap it
						// so the content inside is not lost.
						writer.unwrap( element );

						return true;
					} else if ( element.is( 'element' ) ) {
						// Just added an element. Check its children to see if there are no nested blockQuotes somewhere inside.
						const range = writer.createRangeIn( element );

						for ( const child of range.getItems() ) {
							if ( child.is( 'pre' ) && !schema.checkChild( writer.createPositionBefore( child ), child ) ) {
								writer.unwrap( child );

								return true;
							}
						}
					}
				} else if ( entry.type == 'remove' ) {
					const parent = entry.position.parent;

					if ( parent.is( 'pre' ) && parent.isEmpty ) {
						// Something got removed and now blockQuote is empty. Remove the blockQuote as well.
						writer.remove( parent );

						return true;
					}
				}
			}

			return false;
		} );
	}

	/**
	 * @inheritDoc
	 */
	afterInit() {
		const editor = this.editor;
		const command = editor.commands.get( 'pre' );

		// Overwrite default Enter key behavior.
		// If Enter key is pressed with selection collapsed in empty block inside a quote, break the quote.
		// This listener is added in afterInit in order to register it after list's feature listener.
		// We can't use a priority for this, because 'low' is already used by the enter feature, unless
		// we'd use numeric priority in this case.
		this.listenTo( this.editor.editing.view.document, 'enter', ( evt, data ) => {
			console.log("test")
			const doc = this.editor.model.document;
			const positionParent = doc.selection.getLastPosition().parent;

			if ( doc.selection.isCollapsed && positionParent.isEmpty && command.value ) {
				this.editor.execute( 'pre' );
				this.editor.editing.view.scrollToTheSelection();

				data.preventDefault();
				evt.stop();
			}
		} );
	}
}
