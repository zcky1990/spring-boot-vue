<template>
  <div class="admin">
  <nav-bar> </nav-bar>
  </div>
</template>

<script>
import Navbar from '../widget/Navbar'

export default   {
  name: 'admin',
  props: {
    msg: String
  },
  components: {
            'nav-bar': Navbar
 }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
