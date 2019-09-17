<template>
    <users-page-layout></users-page-layout>
</template>

<script>
import Layout from '@/layout/users-layout'

export default {
    name: 'user-page',
    components: {
        'users-page-layout' : Layout
    }
}
</script>