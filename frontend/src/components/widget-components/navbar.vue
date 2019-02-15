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

<style>
#navbar {
  padding: 30px;
}
#navbar.a {
    font-weight: bold;
    color: #2c3e50;
}
#navbar.router-link-exact-active {
     color: #42b983;
}
</style>
