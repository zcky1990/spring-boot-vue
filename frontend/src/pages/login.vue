<template>
    <login-layout-page></login-layout-page>
</template>

<script>
import LoginLayout from '@/layout/login-layout'

export default {
    name: 'login',
    components: {
        'login-layout-page' : LoginLayout
        },
}
</script>