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
                    <button v-on:click="previewArticle" class="button is-block is-primary">preview</button>  
                </div>
                <div class="level-item">
                    <button v-on:click="test" class="button is-block is-info">Submit</button>  
                </div>
            </div>
        </div>
    </div>

    <div ref="preview" class="modal">
        <div class="modal-background"></div>
        <div class="prev-container">
            <header class="modal-card-head button-container">
                <button v-on:click="hidePreview" class="delete" aria-label="close"></button>
            </header>
            <div class="modal-card-body">
                <div clas="container">
                 <article-prev :title="title" :subtitle="subtitle" :content="content"></article-prev>
                 </div>
            </div>
        </div>
    </div>

    
  </section>
</template>

<script>
//import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import BallonEditor from '@ckeditor/ckeditor5-build-balloon';

//ckeditor5 plugins
import ArticlePrevWidget from './ArticlePreview'

//https://ckeditor.com/docs/ckeditor5/latest/builds/guides/integration/installation.html#npm
//https://ckeditor.com/docs/ckeditor5/latest/builds/guides/integration/frameworks/vuejs.html
export default {
    name: 'editor',
    data() {
            return {
                editor:  BallonEditor,
                editorConfig: {
                   
                },
                editorData: '',
                title:'',
                subtitle:'',
                content :''
            };
        },
    components:{
      'article-prev':ArticlePrevWidget
    },
    methods: {
        test: function(){
            var title = this.$refs.title.value;
            var subtitle = this.$refs.subtitle.value;

            console.log(title)
            console.log(subtitle)
            console.log(this.editorData)
        },
        previewArticle : function(){
            this.title = this.$refs.title.value;
            this.subtitle = this.$refs.subtitle.value;
            this.content = this.editorData;
            this.$refs.preview.style.display="block";
        },
        hidePreview: function(){
            this.$refs.preview.style.display="none";
        }
    }
}
</script>

<style scoped>
.masking-container{
    background:rgba(255,255,255,0.5);
}
.preview-page{
    height:100%;
    background:white;
}
.prev-container{
    margin-top: 20px;
    height: 100vh;
    overflow-y: auto;
}
.modal-background{
    background-color: white;
}
.button-container{
    border-bottom: 1px solid #dbdbdb;
    position: absolute;
    width: 100%;
    top: 0px;
    border-top-left-radius: 0px;
    border-top-right-radius: 0px;
    z-index: 1;
}

</style>
