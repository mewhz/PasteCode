<template>
  <el-row>
    <span style="float:right;">
       <el-link :underline="false" @click="jumpLogin">用户登录</el-link>
    </span>
    <el-col :offset="5" :xs="8" :sm="6" :md="8" :lg="9" :xl="11">
      <div class="page-header">
        <h1>
          贴代码
          <small>paste code</small>
        </h1>
      </div>
    </el-col>
    <el-form
        :model="code"
        :rules="rules"
        ref="code">
      <el-col :offset="5" :xs="8" :sm="6" :md="8" :lg="9" :xl="11">
        <el-form-item prop="codeText">
          <el-input
              type="textarea"
              placeholder="粘贴你的代码到这里"
              :autosize="{minRows: 8, maxRows: 20}"
              v-model="code.codeText">
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :offset="5" :xs="8" :sm="6" :md="8" :lg="9" :xl="11">
        <div class="mt-5">
          <el-select v-model="code.codeType" placeholder="请选择">
            <el-option
                v-for="item in types"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
          <div class="f-l mr-5">
            <el-form-item prop="codeTitle">
              <el-input v-model="code.codeTitle" placeholder="代码标题 (选填)"/>
            </el-form-item>
          </div>
        </div>
        <div class="mt-25">
          <el-button type="primary" @click="submit()">提交</el-button>
          <el-link :underline="false" @click="clickSearch" class="ml-5">搜索代码</el-link>
        </div>
        <el-dialog title="搜索代码" :visible.sync="dialogFormVisible">
            <el-input
                autocomplete="off"
                v-model="searchText"
                placeholder="根据代码标题来查找代码"
            />
          <div slot="footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="search">搜 索</el-button>
          </div>
        </el-dialog>
      </el-col>
    </el-form>
  </el-row>
</template>

<script>

export default {
  name: 'Home',
  data() {
    let checkCodeTitle = (rule, value, callback) => {
      if (value) {
        let temp = value.split(" ");
        if (temp.length !== 1) {
          new callback(new Error("代码标题不能包含空格"));
        }
      }
      callback();
    };
    return {
      id: '',
      code: {
        codeText: '',
        codeType: 'java',
        codeTitle: ''
      },
      types: [{
        value: 'java',
        label: 'Java'
      }, {
        value: 'python',
        label: 'Python'
      }, {
        value: 'cpp',
        label: 'C/C++'
      }],
      rules: {
        codeText: [
            { required: true, whitespace: true, message: "代码不能为空", trigger: ['change', 'blur'] }
        ],
        codeTitle: [
            { validator: checkCodeTitle, trigger: ['change', 'blur'] }
        ],
      },
      dialogFormVisible: false,
      searchText: ''
    }
  },
  methods: {
    // 提交代码
    async submit() {
      let flag = true;
      await this.$refs['code'].validate((valid) => {
        flag = valid;
      });
      if (!flag) return false;
      this.$axios.post('http://127.0.0.1:9090/code/', {
        "codeText": this.code.codeText,
        "codeType": this.code.codeType,
        "codeTitle": this.code.codeTitle
      }).then((res) => {
        console.log(res.data);
        if (200 === res.status) {

          // 弹出的信息提示
          this.$message({
            message: '提交成功！',
            type: 'success',
            duration: 500
          });
        }

        // 间隔 500ms 后跳转
        setTimeout(() => {
          this.codeJump(res.data.codeId);
        }, 500);
      });
    },

    // 跳转到对应的 url
    codeJump(id) {
      this.$router.push('/code/id/' + id);
    },

    // 单击搜索按钮时
    clickSearch() {
      this.dialogFormVisible = true;
      this.searchText = this.code.codeTitle;
      console.log(this.searchText)
    },

    // 根据介绍搜索代码
    search() {
      this.dialogFormVisible = false;
      console.log(this.code.codeTitle);
      this.$router.push('/code/title/' + this.searchText);
    },

    // 跳转到登录界面
    jumpLogin() {
      this.$router.push('/login');
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

small {
  font-size: 80%;
  color: #999;
}

.mt-5 {
  margin-top: 5px;
}

.f-l {
  float: left;
}

.mr-5 {
  margin-right: 5px;
}

.ml-5 {
  margin-left: 5px;
}

.mt-25 {
  margin-top: 25px;
}
</style>