<template>
  <div id="insert-user-form">
    <el-form :model="user" :rules="rules" ref="user">
      <el-form-item label="用户昵称" prop="userName" required>
        <el-input v-model="user.userName" placeholder="请输入用户昵称" size="medium"
        ></el-input>
      </el-form-item>
      <el-form-item label="电子邮箱" prop="userEmail" required>
        <el-input v-model="user.userEmail" placeholder="请输入电子邮箱" size="medium"></el-input>
      </el-form-item>
      <el-form-item label="设置密码" prop="userPassword" required>
        <el-input v-model="user.userPassword" placeholder="请输入用户密码" size="medium"></el-input>
      </el-form-item>
    </el-form>
    <div id="button-group">
      <el-button type="primary" @click="submit">更新</el-button>
      <el-button @click="exit">取消</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "InsertUserForm.vue",
  data() {
    return {
      user: {},
      url: '',
      rules: {
        userName: [
          {required: true, message: '请输入用户昵称', trigger: 'blur'},
        ],
        userEmail: [
          {required: true, message: '请输入电子邮箱', trigger: 'blur'},
          // {pattern: '^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$', message: '请输入正确的电子邮箱', trigger: 'blur'}
        ],
        userPassword: [
          {required: true, message: '请输入用户密码', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    exit() {
      this.$emit("exit");
    },
    async submit() {
      let flag = true;
      await this.$refs['user'].validate((valid) => {
        flag = valid;
      });
      if (!flag) return false;
      this.$axios({
        method: "post",
        url: `${this.$url}/user/register`,
        data: {
          "userName": this.user.userName,
          "userPassword": this.user.userPassword,
          "userEmail": this.user.userEmail
        }
      }).then((response) => {
        let resp = response.data;
        if (resp.flag === true) {
          resp = resp.data;
          this.$alert("你的账号是: " + resp.userAccount, '注册成功',{
            confirmButtonText: '我记下来了',
            center: true,
            type: 'success',
          }).then(() => {
            this.$emit("reload");
          })
        } else {
          this.$message({
            message: resp.message,
            type: 'warning',
            duration: 1000
          });
        }
      })
    }
  },
  mounted() {
  }
}
</script>

<style scoped>

>>> .el-form-item {
  margin-bottom: 15px !important;
}

#button-group {
  margin-top: 20px;
  text-align: center;
}
</style>