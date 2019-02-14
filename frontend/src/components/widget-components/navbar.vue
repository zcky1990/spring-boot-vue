<template>
    <div id="nav">
      <router-link to="/">Hello</router-link> |
      <router-link to="/admin">Admin</router-link>
    </div>
</template>

<script>
export default {
  name: 'navbar',
  data () {
    return {
    }
  }
}
</script>

<style lang="scss">
#navbar {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;
    &.router-link-exact-active {
     color: #42b983;
    }
  }
}
</style>
