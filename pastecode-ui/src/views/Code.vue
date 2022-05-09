<template>
  <div>
    <h1>Code</h1>
    <div class="line-numbers rainbow-braces match-braces no-brace-hover no-brace-select">
      <pre><code :class=codeClass v-text="codeText"/></pre>
    </div>
  </div>
</template>

<script>

import Prism from 'prismjs';
import 'prismjs/components/prism-java.js';
import 'prismjs/components/prism-c.min.js';
import 'prismjs/components/prism-cpp.min.js';
import 'prismjs/plugins/match-braces/prism-match-braces.js';
import 'prismjs/plugins/show-language/prism-show-language.js';
export default {
  name: "Code",
  data() {
    return {
      id: "",
      codeText: '',
      codeClass: '',
    }
  },
  methods: {
    load() {
      this.$axios.get('http://127.0.0.1:9090/code/' + this.id).then((res) => {
        this.codeText = res.data.codeText;
        this.codeClass = `language-${res.data.codeType} show-language`;
        console.log(this.codeText);
        this.$nextTick(() => {
          // 将回调函数放到下一个节点执行
          Prism.highlightAll();
          // this.$forceUpdate();
        });
      }).catch((err) => {
        console.log(err);
      });
    }
  },
  created() {
    this.id = this.$route.params.id;
    this.load();
  }
}
</script>

<style scoped>

</style>