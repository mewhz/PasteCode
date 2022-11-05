<template>
<div>
  <el-input v-model="codeText"></el-input>
  <el-button @click="myCopy">点我复制</el-button>
</div>
</template>

<script>
import Copy from 'copy-to-clipboard';
export default {
  name: "About",
  data() {
    return {
      codeText: 'public static void main(String[] args){\n' +
          '            System.our.println("Hello World");\n' +
          '        }'
    }
  },
  methods: {
    myCopy() {
      console.log("开始复制:" + this.codeText);
      let flag = Copy(this.codeText);
      console.log("复制完成:", flag);
    }
  }
}
</script>

<style scoped>

</style>