    <template>
  <div class="admin-page">
    <acess-level-form></acess-level-form>
    <category-form></category-form>
    <role-form></role-form>
    <editor-component v-bind:key="id" v-bind:addUrl="addUrl" v-bind:updateUrl="updateUrl"></editor-component>
    <table-component :title="title" :tableHeaderList="tableHeaderList" :dataList="dataList"></table-component>
  </div>
</template>

    <script>
//import {AXIOS} from '@/components/http-common'
//import Navbar from "@/components/widget/Navbar";
import Editor from "@/components/widget/Editor";
import TableComponent from "@/components/widget/Table";
import RolesForm from "@/components/widget/admin/RolesForm"
import CategoryForm from "@/components/widget/admin/CategoryForm"
import AccessLevelForm from "@/components/widget/admin/AccessLevelForm"

export default {
  name: "admin",
  data() {
    return {
      id: 1,
      addUrl: "article/add_article",
      updateUrl: "article/update_article",
      title:"asdasdad",
      tableHeaderList: [
        {
          text: "Dessert (100g serving)",
          align: "left",
          sortable: false,
          value: "name"
        },
        { text: "Calories", value: "calories" },
        { text: "Fat (g)", value: "fat" },
        { text: "Carbs (g)", value: "carbs" },
        { text: "Protein (g)", value: "protein" },
        { text: "Iron (%)", value: "iron" }
      ],
      dataList: [
          {
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            iron: '1%',
          },
          {
            name: 'Ice cream sandwich',
            calories: 237,
            fat: 9.0,
            carbs: 37,
            protein: 4.3,
            iron: '1%',
          },
          {
            name: 'Eclair',
            calories: 262,
            fat: 16.0,
            carbs: 23,
            protein: 6.0,
            iron: '7%',
          },
          {
            name: 'Cupcake',
            calories: 305,
            fat: 3.7,
            carbs: 67,
            protein: 4.3,
            iron: '8%',
          },
          {
            name: 'Gingerbread',
            calories: 356,
            fat: 16.0,
            carbs: 49,
            protein: 3.9,
            iron: '16%',
          },
          {
            name: 'Jelly bean',
            calories: 375,
            fat: 0.0,
            carbs: 94,
            protein: 0.0,
            iron: '0%',
          },
          {
            name: 'Lollipop',
            calories: 392,
            fat: 0.2,
            carbs: 98,
            protein: 0,
            iron: '2%',
          },
          {
            name: 'Honeycomb',
            calories: 408,
            fat: 3.2,
            carbs: 87,
            protein: 6.5,
            iron: '45%',
          },
          {
            name: 'Donut',
            calories: 452,
            fat: 25.0,
            carbs: 51,
            protein: 4.9,
            iron: '22%',
          },
          {
            name: 'KitKat',
            calories: 518,
            fat: 26.0,
            carbs: 65,
            protein: 7,
            iron: '6%',
          } 
        ]
    };
  },
  components: {
    "editor-component": Editor,
    "table-component": TableComponent,
    "role-form": RolesForm,
    "category-form": CategoryForm,
    "acess-level-form":AccessLevelForm
  }
};
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
