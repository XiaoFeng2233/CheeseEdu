<template>
    <div class="layout">
      <LayoutHeader/>
      <div class="main">
        <router-view/>
      </div>
      <LayoutFooter style="margin-top: 40px"/>
    </div>
</template>

<script>
import LayoutHeader from "../components/common/LayoutHeader.vue";
import LayoutFooter from "../components/common/LayoutFooter.vue";
export default{
  name:"Default",
  components:{
    LayoutHeader,
    LayoutFooter
  }
}
</script>
<style scoped lang="scss">
.layout{
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main{
  flex:1;
}
</style>