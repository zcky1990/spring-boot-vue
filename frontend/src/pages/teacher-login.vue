<template>
    <login-layout-page></login-layout-page>
</template>

<script>
import LoginLayout from '@/layout/teacher-login-layout'

export default {
    name: 'teacher-login',
    components: {
        'login-layout-page' : LoginLayout
        },
    }
</script>