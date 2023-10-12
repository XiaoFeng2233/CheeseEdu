<template>
    <router-view :key="$route.fullPath"/>
</template>

<script>


import {SiteInit} from "./Utils/SiteInit";

export default {
  setup() {
    SiteInit()
  }
}
</script>

<style scoped lang="scss">

</style>
