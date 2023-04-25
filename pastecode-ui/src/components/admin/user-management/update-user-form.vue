<template>
  <div id="insert-user-form">
    <el-form :model="user" :rules="rules" ref="user" inline>
      <el-form-item id="update">
        <el-upload
            :action="url"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
          <el-image v-if="imageUrl" :src="imageUrl"></el-image>
        </el-upload>
      </el-form-item>
      <el-form-item label="用户账号" prop="userAccount" style="margin-left: 11px;">
        <el-input v-model="user.userAccount" size="medium" disabled></el-input>
      </el-form-item>
      <el-form-item label="用户昵称" prop="userName" required>
        <el-input v-model="user.userName" placeholder="请输入用户昵称" size="medium"></el-input>
      </el-form-item>
      <el-form-item label="电子邮箱" prop="userEmail" required>
        <el-input v-model="user.userEmail" placeholder="请输入电子邮箱" size="medium"></el-input>
      </el-form-item>
      <el-form-item id="select" label="用户权限" required>
        <el-select v-model="user.userRoleStr">
          <el-option
              key="1"
              label="管理员"
              value="管理员">
          </el-option>
          <el-option
              key="0"
              label="普通用户"
              value="普通用户">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="修改密码" prop="userPassword" style="margin-left: 11px;">
        <el-input v-model="user.userPassword" placeholder="请输入用户密码" size="medium"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="userAgainPassword" style="margin-left: 11px;">
        <el-input v-model="user.userAgainPassword" placeholder="请再次输入密码" size="medium"></el-input>
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
  name: "UpdateUserForm.vue",
  props: {
    userProp: {
      type: Object
    }
  },
  data() {
    return {
      user: this.userProp,
      imageUrl: '',
      url: `${this.$url}/user/upload`,
      rules: {
        userName: [
          {required: true, message: '请输入用户昵称', trigger: 'blur'},
        ],
        userEmail: [
          {required: true, message: '请输入电子邮箱', trigger: 'blur'},
          // {pattern: '^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$', message: '请输入正确的电子邮箱', trigger: 'blur'}
        ],
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


      this.userRole = this.userRoleStr === "管理员" ? 1 : 0;

      console.log("user:", JSON.stringify(this.user))

      // this.$axios({
      //   method: "post",
      //   url: `${this.$url}/user/update`,
      //   data: {
      //     "userName": this.user.userName,
      //     "userPassword": this.user.userPassword,
      //     "userEmail": this.user.userEmail,
      //     "userRole": this.user.userRole
      //   }
      // }).then((response) => {
      //   let resp = response.data;
      //   if (resp.flag === true) {
      //     resp = resp.data;
      //     this.$alert("你的账号是: " + resp.userAccount, '注册成功',{
      //       confirmButtonText: '我记下来了',
      //       center: true,
      //       type: 'success',
      //     }).then(() => {
      //       this.$emit("reload");
      //     })
      //   } else {
      //     this.$message({
      //       message: resp.message,
      //       type: 'warning',
      //       duration: 1000
      //     });
      //   }
      // })
    },
    // 上传成功回调
    handleAvatarSuccess(res, file) {
      console.log("res:", res.data);

      this.imageUrl = require("@/assets/" + res.data);

      this.user.userAvatar = res.data;
    },
    // 上传前格式和图片大小限制
    beforeAvatarUpload(file) {
      console.log(file);
      // const type = file.type === 'image/jpeg' || 'image/jpg' || 'image/webp' || 'image/png'
      // const isLt2M = file.size / 1024 / 1024 < 2
      // if (!type) {
      //   this.$message.error('图片格式不正确!(只能包含jpg，png，webp，JPEG)')
      // }
      // if (!isLt2M) {
      //   this.$message.error('上传图片大小不能超过 2MB!')
      // }
      // return type && isLt2M
    },
    cssLoad() {
      // 设置用户权限的选择框大小与其他输入框大小相同
      let item1 = document.querySelector(".el-form-item__content");
      let item2 = document.querySelector("#select > .el-form-item__content");
      console.log(item1, item2);
      item2.style.width = window.getComputedStyle(item1, null).width;
    },
  },
  created() {
    this.imageUrl = require("@/assets/" + this.user.userAvatar);
    this.cssLoad();
  }
}
</script>

<style scoped>

>>> .el-form-item {
  margin-bottom: 15px !important;
}
.el-form {
  text-align: center;
}
#button-group {
  margin-top: 20px;
  text-align: center;
}
.el-image {
  width: 250px;
  height: 250px;
}
#update {
  float: right;
}
</style>