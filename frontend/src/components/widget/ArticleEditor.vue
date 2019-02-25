<template>
    <section class="section editor">
    <div class="box">  
        <div>  
        <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
        </div>

        <div class="card-content">
            <div class="level-right">
                <div class="level-item">
                    <button v-on:click="test" class="button is-block is-primary">preview</button>  
                </div>
                <div class="level-item">
                    <button v-on:click="test" class="button is-block is-info">Submit</button>  
                </div>
            </div>
        </div>
    </div>
  </section>
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default {
    name: 'editor',
    data() {
            return {
                editor:  ClassicEditor,
                editorData: '',
                editorConfig: {
                    // The configuration of the rich-text editor.
                }
            };
        },
    methods: {
        test: function(){
            console.log(this.editorData)
        }
    }
}
</script>

<style scoped>

</style>
