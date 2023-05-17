<template>
  <div id="login">
    <el-col :span="8" :offset="8">
      <h1>用户注册</h1>
      <el-form :model="user" :rules="rules" ref="ruleForm">
        <el-form-item prop="name">
          <el-input type="text" v-model="user.name" autocomplete="off" placeholder="用户名"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="user.password" autocomplete="off" placeholder="密码"/>
        </el-form-item>
        <el-form-item prop="email">
          <el-input type="text" v-model="user.email" autocomplete="off" placeholder="邮箱"/>
        </el-form-item>
      </el-form>
      <el-button @click="submit">注册</el-button>
    </el-col>
  </div>
</template>

<script>

export default {
  name: 'Register',
  data() {
    return {
      user: {
        name: '',
        password: '',
        email: '',
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          {pattern: '^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$', message: '请输入正确的电子邮箱', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {

    // 提交
    async submit() {
      let flag = true;
      await this.$refs['ruleForm'].validate((valid) => {
        if (!valid) {
          flag = false;
        }
      });


      if (!flag) return;
      console.log(this.user)
      this.$axios({
        method: "post",
        url: 'http://127.0.0.1:9090/user/register/',
        data: {
          "userName": this.user.name,
          "userPassword": this.user.password,
          "userEmail": this.user.email
        },
      }).then((response) => {

        console.table(response.data);

        let resp = response.data;

        if (20000 === resp.code) {

          // 获取响应数据中的数据字段
          resp = resp.data;

          console.table(resp);

          localStorage.setItem("userId", resp.userId);
          localStorage.setItem("userAccount", resp.userAccount);
          localStorage.setItem("userName", resp.userName);

          // 弹出的信息提示
          this.$alert("你的账号是: " + resp.userAccount, '注册成功',{
            confirmButtonText: '我记下来了',
            center: true,
            type: 'success',
          });

          setTimeout(() => {
            this.userJump();
          }, 500);

        } else {
          this.$message({
            message: resp.message,
            type: 'warning',
            duration: 1000
          });
        }
      });
    },

    // 跳转
    async userJump() {
      // localStorage.setItem("userStatus", this.user.status);
      // localStorage.setItem("uid", this.user.username);
      console.log("this.path:", localStorage.getItem("path"));
      let path = localStorage.getItem("path");
      if (path === null) {
        await this.$router.push('/');
      }
      else {
        await this.$router.push(path);
      }
    },

    // 注册
  },

  mounted() {
    document.addEventListener('keyup', (e) => {
      if (e.key === "Enter") {
        this.submit();
      }
    });
  }

}
</script>
<style>
</style>
