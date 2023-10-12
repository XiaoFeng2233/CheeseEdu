<template>
  <div ref='editor'></div>
</template>

<script>
import {onMounted, onBeforeUnmount, ref, reactive,watch} from 'vue';
import WangEditor from 'wangeditor';

export default {
  name: 'MyEditor',
  props: {
    html: String,
    text: String
  },
  emits: ["update:html", "update:text"],
  setup(props,context) {
    const editor = ref();
    const content = reactive({
      html: '',
      text: '',
    });
    let instance;
    onMounted(() => {
      instance = new WangEditor(editor.value);
      Object.assign(instance.config, {
        onchange() {
          content.html = instance.txt.html();
          content.text = instance.txt.text();
        },
      });
      instance.create();
      instance.txt.html(props.html)
    });
    onBeforeUnmount(() => {
      instance.destroy();
      instance = null;
    });

    watch(()=>props.html,(newval,oldval)=>{
      instance.txt.html(newval)
    })
    watch(()=>content.html,()=>{
      context.emit('update:html', content.html)
    })
    watch(()=>content.text,()=>{
      context.emit('update:text', content.text)
    })

    return {
      editor,
      content,
    };
  },
};
</script>