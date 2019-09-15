    <template>
        <teacher-profile-layout-page></teacher-profile-layout-page>
    </template>

    <script>
    import ProfileLayout from '@/layout/teacher-profile-layout'

    export default {
    name: 'teacher-login',
    components: {
        'teacher-profile-layout-page' : ProfileLayout
        },
    }
    </script>
    <style scoped>
    h3 {
    margin: 40px 0 0;
    }
    ul {
    list-style-type: none;
    padding: 0;
    }
    li {
    display: inline-block;
    margin: 0 10px;
    }
    a {
    color: #42b983;
    }
    </style>
