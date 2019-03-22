<template>
 <section class="section">
      <div class="columns is-order-2">
        <div class="column box is-one-quarter">
          satu
        </div>
        
        <div class="column box is-order-1">
          dua
        </div>
        
        <div class="column box is-one-quarter is-order-3">
          tiga
        </div>
      </div>
   
  </section>
 </template>
 
 <script>
 	// JS fiddle : https://jsfiddle.net/eL4gvp1c/
export default {
    name: 'articlehome',
    data() {
    		return {
    			message: 'Hello Vue.js!',
    			title:'How to create application boilerplate with Vert.x, VueJS, and OAuth2 in five steps',
    			topics:'Javascript',
    			short_desc : '<p>In this tutorial you will learn how to setup an application boilerplate based on Vert.x (Java) as a backend and VueJs as a frontend with a focus on User Authentication against Keycloak through OAuth2.</p>',
    			image : 'https://res.cloudinary.com/dk0z4ums3/image/upload/w_667,h_336,c_fill/v1545292047/attached_image/alergi-si-kecil-sering-kambuh-bisa-jadi-karena-salah-minum-susu.jpg',
    			authors: 'Jack Cohen in OneZero',
    			date_created: 'Nov 16, 2018'
    		}
    	
  		}
  	}
</script>

 <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>
    .is-order-1 {
        order: 1;
    }
    .is-order-2 {
        order: 2;
    }
    .is-order-3 {
        order: 3;
    }
    </style>

