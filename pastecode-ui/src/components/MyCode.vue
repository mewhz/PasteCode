<template>
  <div class="line-numbers rainbow-braces match-braces no-brace-hover no-brace-select">
    <pre><code :class=codeClass v-text="codeText"/></pre>
    <el-button type="primary" size="mini" @click="copyCode">点我复制代码</el-button>
  </div>
</template>
<script>

import Prism from 'prismjs';
import 'prismjs/components/prism-java.js';
import 'prismjs/components/prism-c.min.js';
import 'prismjs/components/prism-cpp.min.js';
import 'prismjs/plugins/match-braces/prism-match-braces.js';
import 'prismjs/plugins/show-language/prism-show-language.js';
import Copy from 'copy-to-clipboard'

export default {
  name: "Code",
  props: {
    codeText: {
      type: String,
      required: true
    },
    codeClass: {
      type: String,
      required: true
    }
  },
  data() {
    return {
    }
  },
  methods: {
    copyCode() {
      let flag = Copy(this.codeText);
      console.log("复制成功：", flag);
      if (flag) {
        this.$message({
          message: '复制成功, 快去粘贴给小伙伴吧!!',
          type: 'success',
          duration: 1200
        });
      }
      else {
        this.$message({
          message: '复制失败, 请联系管理员..',
          type: 'error',
          duration: 1200
        });
      }

    }
  },
  created() {
    console.log(this.codeClass);
  }
}
</script>

<style scoped>

</style>