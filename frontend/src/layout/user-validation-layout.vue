    <template>
    <div class="validation-page-container">
      <user-validation-component :id-user="idUser"></user-validation-component>
    </div>
    </template>
    <script>
import ValidationComponents from '@/components/widget/user-validation-components'

export default   {
  name: 'user-validation-page-layout',
    props:{
      idUser: {
        type: String,
        default: ""
      }
    },
    components: {
      "user-validation-component" : ValidationComponents
    }
}
</script>

<style scoped>
</style>
