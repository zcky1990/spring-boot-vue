import Plugin from '@ckeditor/ckeditor5-core/src/plugin';
import CodeBlockEditing from './codeblockediting';
import CodeBlockUI from './codeblockui';

export default class CodeBlock extends Plugin {
	/**
	 * @inheritDoc
	 */
	static get requires() {
		return [ CodeBlockEditing, CodeBlockUI ];
	}

	/**
	 * @inheritDoc
	 */
	static get pluginName() {
		return 'CodeBlock';
	}
}
