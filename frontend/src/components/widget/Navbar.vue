    <template>
    <section class="hero is-primary is-medium">
    <!-- Hero head: will stick at the top -->
    <div class="hero-head">
        <nav class="navbar">
        <div class="container">
            <div class="navbar-brand">
            <a class="navbar-item">
                <img src="https://bulma.io/images/bulma-type-white.png" alt="Logo">
            </a>
            <span v-on:click="showMenuBurger" class="navbar-burger burger" data-target="navbarMenuHeroA">
                <span></span>
                <span></span>
                <span></span>
            </span>
            </div>
            <div ref="menu" id="navbarMenuHeroA" class="navbar-menu">
            <div class="navbar-end">
                <a class="navbar-item">
                Home
                </a>
                <a class="navbar-item">
                Examples
                </a>
                <a class="navbar-item">
                Documentation
                </a>
                <span class="navbar-item">
                <a class="button is-primary is-inverted">
                    <span class="icon">
                    <i class="fab fa-github"></i>
                    </span>
                    <span>Download</span>
                </a>
                </span>
            </div>
            </div>
        </div>
        </nav>
    </div>
    </section>
    </template>

    <script>
    export default {
    name: 'navbar',
    props: {
        msg: String
    },
    methods: {
        showMenuBurger: function () {
            var element = this.$refs.menu;
            if(element.classList.contains('is-active')){
                element.classList.remove('is-active');
            } else{
                element.classList.add('is-active');
            }
        }
    }
    }
    </script>

    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>

    </style>
