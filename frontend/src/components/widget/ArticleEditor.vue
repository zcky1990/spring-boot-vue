<template>
    <section class="section editor">
    <div class="box">  
        <div class="field">
            <div class="control">
                <input ref="title" class="input is-large" type="text" placeholder="Title">
            </div>
            </div>
        <div class="field">
            <div class="control">
                <input ref="subtitle" class="input" type="text" placeholder="Subtitle">
            </div>
        </div>
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

//https://ckeditor.com/docs/ckeditor5/latest/builds/guides/integration/frameworks/vuejs.html
export default {
    name: 'editor',
    data() {
            return {
                editor:  ClassicEditor,
                editorData: '',
                editorConfig: {

                }
            };
        },
    methods: {
        test: function(){
            var title = this.$refs.title.value;
            var subtitle = this.$refs.subtitle.value;

            console.log(title)
            console.log(subtitle)

            console.log(this.editorData)
        }
    }
}
</script>

<style scoped>

</style>
