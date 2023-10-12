<template>
  <div v-html="content">

  </div>
</template>

<script>
import store from "../../store/store.js";
import {computed, ref} from "vue";
export default{
  name:"LayoutFooter",
  setup(){
    const content = computed(()=>{
      return store.state.siteconfig.siteFooter
    })

    return {
      content
    }
  }
}
</script>
<style scoped lang="scss">
  .footer{
    background-color: #2e2e2e;
    padding: 40px 0;
    color: white;
  }
</style>