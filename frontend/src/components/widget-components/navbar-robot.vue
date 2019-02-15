<template>
    <header id="header" class="header-scrolled">
			    <div class="container main-menu">
			    	<div class="row align-items-center justify-content-between d-flex">
				      <div id="logo">
				        <a href="index.html"><img src="img/logo.png" alt="" title=""></a>
				      </div>
				      <nav id="nav-menu-container">
				        <ul class="nav-menu sf-js-enabled sf-arrows" style="touch-action: pan-y;">
				          <li class="menu-active"><a href="index.html">Home</a></li>
				          <li><a href="about.html">About Us</a></li>
				          <li><a href="services.html">Services</a></li>
				          <li><a href="products.html">Products</a></li>
				          <li class="menu-has-children"><a href="" class="sf-with-ul">Blog</a>
				            <ul style="display: none;">
				              <li><a href="blog-home.html">Blog Home</a></li>
				              <li><a href="blog-single.html">Blog Single</a></li>
				            </ul>
				          </li>						          
				          <li><a href="contact.html">Contact</a></li>
				          <li class="menu-has-children"><a href="" class="sf-with-ul">Dropdown</a>
				            <ul style="display: none;">
				              <li><a href="elements.html">Elements</a></li>	
				              <li><a href="product-details.html">Product Details</a></li>
				              <li class="menu-has-children"><a href="" class="sf-with-ul">Level 2</a>
					            <ul style="display: none;">
					              <li><a href="#">Item 1</a></li>
					              <li><a href="#">Item 2</a></li>
					            </ul>
					          </li>	
				            </ul>
				          </li>				              
				        </ul>
				      </nav><!-- #nav-menu-container -->		    		
			    	</div>
			    </div>
			  </header>
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
#header.header-scrolled {
    background: rgba(138, 144, 255, 0.9);
    transition: all 0.5s;
}
#header {
    padding: 0px 0px 20px 0px;
    position: fixed;
    left: 0;
    top: 0;
    right: 0;
    transition: all 0.5s;
    z-index: 997;
}
.main-menu {
    padding-top: 20px;
}
#nav-menu-container {
    margin: 0;
}
.nav-menu, .nav-menu * {
    margin: 0;
    padding: 0;
    list-style: none;
}
.nav-menu > li {
    margin-left: 10px;
}

.nav-menu > li {
    float: left;
}
.nav-menu li {
    position: relative;
    white-space: nowrap;
}
</style>
