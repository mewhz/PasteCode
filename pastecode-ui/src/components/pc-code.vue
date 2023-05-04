<template>
  <div class="line-numbers rainbow-braces match-braces no-brace-hover no-brace-select">
    <pre id="print"><code :class='codeClass' v-text="code.codeText"/></pre>

  </div>
</template>
<script>

import 'prismjs/components/prism-java.js';
import 'prismjs/components/prism-c.min.js';
import 'prismjs/components/prism-cpp.min.js';
import 'prismjs/plugins/match-braces/prism-match-braces.js';
import 'prismjs/plugins/show-language/prism-show-language.js';
import Copy from 'copy-to-clipboard'
import Prism from "prismjs";

export default {
  name: "PcCode",
  props: {
    code: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      codeClass: `language-${this.code.codeType} show-language`
    }
  },
  methods: {

  },
  created() {
    this.$nextTick(() => {
      console.log("next");
      // 将回调函数放到下一个节点执行
      Prism.highlightAll();
    });
  }
}
</script>

<style scoped>
.el-button {
  margin-left: 15px;
}
</style>