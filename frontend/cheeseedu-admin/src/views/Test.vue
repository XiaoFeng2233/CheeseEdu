<template>
  <div>
    <a-button :disabled="isupload" @click="gogo" type="primary">
      <span v-if="!isupload">上传</span>
      <span v-else>上传中  {{ process }}</span>
    </a-button>
    <input type="file" @change="onchange"/>
  </div>
</template>

<script>
import axios from "../axios/axios.js"
import {ref} from "vue";

export default {
  name: "Test",
  setup() {
    const myFile = ref(undefined);
    const isupload = ref(false)
    const process = ref(undefined)

    const onchange = (e) => {
      myFile.value = e.target.files[0]
    }
    const gogo = () => {
      isupload.value = true
      let data = new FormData()
      data.append("file", myFile.value)
      axios({
        url: "https://document-1253679257.cos.ap-shanghai.myqcloud.com/videos/2021-10-26/119176c972894de5868f09f8b7710429.mp4?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDPeiaIoPGCvSpzwGFer5DAvVt79ye4Xjj%26q-sign-time%3D1635254129%3B1635426929%26q-key-time%3D1635254129%3B1635426929%26q-header-list%3Dhost%26q-url-param-list%3D%26q-signature%3D9a95805796201018031da0fbdde269a5d52fa7c9",
        method: "put",
        timeout: 10 * 24 * 60 * 60 * 1000,
        data: data,
        onUploadProgress: (progressEvent) => {
          let a = Math.floor((progressEvent.loaded / progressEvent.total) * 10000)/100;
          process.value = a + "%"
        }
      }).then(res => {
        process.value = 0;
        isupload.value = false
      })
    }
    return {
      gogo,
      onchange,
      isupload,
      process
    }
  }
}
</script>
<style scoped>

</style>