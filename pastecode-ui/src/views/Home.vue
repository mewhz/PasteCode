<template>
  <el-row>
    <el-col :offset="5" :xs="8" :sm="6" :md="8" :lg="9" :xl="11">
        <div class="page-header" >
          <h1>
            贴代码
            <small>paste code</small>
          </h1>
        </div>
    </el-col>
    <el-col :offset="5" :xs="8" :sm="6" :md="8" :lg="9" :xl="11">
        <el-input
            type="textarea"
            placeholder="粘贴你的代码到这里"
            :autosize="{minRows: 8, maxRows: 20}"
            v-model="textarea">
        </el-input>
    </el-col>
    <el-col :offset="5" :xs="8" :sm="6" :md="8" :lg="9" :xl="11">
        <div class="mt-5">
          <el-select v-model="value" placeholder="请选择">
            <el-option
                v-for="item in types"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
          <div class="f-l mr-5">
            <el-input v-model="id" placeholder="代码 id" />
          </div>
        </div>
      <div class="mt-5">
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      textarea: 'print("Hello World!")',
      types: [{
        value: 'java',
        label: 'Java'
      },{
        value: 'python',
        label: 'Python'
      },{
        value: 'cpp',
        label: 'C/C++'
      }],
      value: 'python',
      id: '',
    }
  },
  methods: {
    submit() {
      console.log(this.value);
      this.$axios.post('http://127.0.0.1:9090/code/', {
        "codeText": this.textarea,
        "codeType": this.value,
      }).then((res) => {
        console.log(res.data);
        if (200 === res.status) {
          this.$message({
            message: '提交成功！',
            type: 'success'
          });
        }
        this.jump(res.data.codeId);
      });
    },
    // 提交代码
    jump(id) {
      this.$router.push('/code/' + id);
    }
  },
}
</script>

<style>
.page-header {
  padding-bottom: 9px;
  margin: 40px 0 20px;
  border-bottom: 1px solid #eee
}
small{
  font-size: 80%;
  color: #999;
}
.mt-5{
  margin-top: 5px;
}
.f-l{
  float: left;
}
.mr-5{
  margin-right: 5px;
}
</style>