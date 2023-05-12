<template>
  <div id="diff">
    <pc-header></pc-header>
    <el-card class="item">
      <label class="title">语言</label>
      <el-select class="value" v-model="modePath" @change="handleModelPathChange" size="mini" value-key="name">
        <el-option v-for="mode in modeArray"
                   :key="mode.name"
                   :label="mode.name"
                   :value="mode.path">
        </el-option>
      </el-select>
      <el-button icon="el-icon-refresh"
                 size="small"
                 round
                 style="margin-left: 5px"
                 @click="refresh">重置</el-button>
    </el-card>
    <div class="ace-editor" ref="old" @blur="compare"></div>
    <div class="ace-editor" ref="new"></div>
    <div id="code-diff">
      <code-diff
          :old-string="oldString"
          :new-string="newString"
          output-format="side-by-side"
          :language="language"/>
    </div>
  </div>
</template>

<script>
import ace from 'ace-builds'
import 'ace-builds/src-noconflict/snippets/c_cpp'
import 'ace-builds/src-noconflict/snippets/java'
import 'ace-builds/webpack-resolver'
import 'ace-builds/src-noconflict/ext-language_tools'
import 'ace-builds/src-noconflict/theme-monokai'
import 'ace-builds/src-noconflict/mode-javascript'
import PcHeader from "@/components/pc-header";


const wrapArray = [{
  name: '开启',
  value: true
}, {
  name: '关闭',
  value: false
}]

const modeArray = [{
  name: 'C/C++',
  path: 'ace/mode/c_cpp'
}, {
  name: 'Java',
  path: 'ace/mode/java'
}, {
  name: 'Python',
  path: 'ace/mode/python'
}]

export default {
  name: "Run",
  components: {PcHeader},
  props: {
    value: String
  },
  mounted () {
    this.aceOldEditor = ace.edit(this.$refs.old, {
      maxLines: 30,
      minLines: 20,
      fontSize: 14,
      value: this.value ? this.value : '',
      theme: this.themePath,
      mode: this.modePath,
      wrap: this.wrap,
      tabSize: 4
    })
    // 激活自动提示
    this.aceOldEditor.setOptions({
      enableSnippets: true,
      enableLiveAutocompletion: true,
      enableBasicAutocompletion: true
    })


    this.aceNewEditor = ace.edit(this.$refs.new, {
      maxLines: 30,
      minLines: 20,
      fontSize: 14,
      value: this.value ? this.value : '',
      theme: this.themePath,
      mode: this.modePath,
      wrap: this.wrap,
      tabSize: 4
    })
    // 激活自动提示
    this.aceNewEditor.setOptions({
      enableSnippets: true,
      enableLiveAutocompletion: true,
      enableBasicAutocompletion: true
    })

    this.aceOldEditor.getSession().on('change', this.compare);
    this.aceNewEditor.getSession().on('change', this.compare);
  },
  data () {
    return {
      runFlag: false,
      aceOldEditor: null,
      aceNewEditor: null,
      toggle: false,
      wrap: true,
      language: 'cpp',
      themePath: 'ace/theme/monokai',
      modePath: 'ace/mode/c_cpp',
      modeArray: modeArray,
      wrapArray: wrapArray,
      input: '',
      output: '',
      userId: 0,
      oldString: '',
      newString: '',
    }
  },
  methods: {

    compare() {
      this.oldString = this.aceOldEditor.getSession().getValue();
      this.newString = this.aceNewEditor.getSession().getValue();
    },

    refresh() {
      this.aceNewEditor.getSession().setValue('');
      this.aceOldEditor.getSession().setValue('');
    },

    send() {
      console.log(this.userId);
      if (this.userId === null || this.userId === 0) {
        this.$confirm('您还未登录, 是否登录?', '系统提示', {
          confirmButtonText: '登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push('/login');
        });
        return;
      }

      this.runFlag = true;

      let codeText = this.aceEditor.getSession().getValue();
      let codeType;
      this.userId = localStorage.getItem("userId");

      if (this.modePath === "ace/mode/c_cpp")
        codeType = "cpp";
      else if (this.modePath === "ace/mode/java")
        codeType = "java";
      else if (this.modePath === "ace/mode/python")
        codeType = "python";


      this.$axios({
        url: `${this.$url}/run/`,
        method: 'post',
        data: {
          "codeText": codeText,
          "codeType": codeType,
          "runInput": this.input,
          "codeAuthorId": this.userId
        }
      }).then((response) => {
        console.log(response.data);

        if (response.data.flag === true) {
          let resp = response.data.data;


          console.log(resp.runError === "")

          this.output = resp.runError === "" ? resp.runOutput.split("\r\n") : resp.runError.split("\r\n");

          this.runFlag = false;

        }
        console.log(this.output);
      })
    },

    toggleConfigPanel () {
      this.toggle = !this.toggle
    },
    change () {
      this.$emit('input', this.aceEditor.getSession().getValue())
    },
    handleModelPathChange (modelPath) {
      this.aceNewEditor.getSession().setMode(modelPath)
      this.aceOldEditor.getSession().setMode(modelPath)

      if (modelPath === "ace/mode/c_cpp")
        this.language = "cpp";
      else if (modelPath === "ace/mode/java")
        this.language = "java";
      else if (modelPath === "ace/mode/python")
        this.language = "python";
    },
    handleWrapChange (wrap) {
      this.aceEditor.getSession().setUseWrapMode(wrap)
    }
  }
}
</script>

<style lang='scss' scoped>
// /deep/ 深度选择器, 根据父元素深度的找到需要改变的子元素
.el-card /deep/ .el-card__body {
  padding: 8px!important;
  //margin-left: 42%;
  font-size: 20px;
}
.hint {
  font-size: 20px;
}
.ace-container {
  position: relative;

  .config-panel {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 50%;
    height: 100%;
    overflow: scroll;
    box-shadow: grey -5px 2px 3px;
    background-color: rgba(255, 255, 255, 0.5);
    z-index: 1;
  }

  .bookmarklet {
    //position: absolute;
    right: 0;
    bottom: 0;
    width: 20px;
    height: 20px;
    z-index: 2;
    cursor: pointer;
    border-width: 9px;
    border-style: solid;
    border-color: lightblue gray gray rgb(206, 173, 230);
    border-image: initial;
  }
}
.ace-editor {
  width: 48%;
  float: left;
  margin-right: 1%;
  margin-left: 1%
}
.item {
  float: right;
  width: 100%;
}
.code-diff-view {
  width: 98%;
  margin-left: 1%;
}
.code-diff-view /deep/ .file-header {
  padding: 0 !important;
}
</style>
