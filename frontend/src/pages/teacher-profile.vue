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