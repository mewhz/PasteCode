<template>
  <div id="user-management">
    <el-form inline ref="userForm" :model="user">
      <el-form-item label="用户账号" prop="userAccount">
        <el-input placeholder="请输入用户账号" v-model="user.userAccount"></el-input>
      </el-form-item>
      <el-form-item label="用户昵称" prop="userName">
        <el-input placeholder="请输入用户昵称" v-model="user.userName"></el-input>
      </el-form-item>
      <el-form-item label="用户邮箱" prop="userEmail">
        <el-input placeholder="请输入用户邮箱" v-model="user.userEmail"></el-input>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="load">搜索</el-button>
      <el-button icon="el-icon-refresh" @click="refresh">重置</el-button>
      <el-button plain size="small " type="primary" @click="insert">新增</el-button>
      <el-button plain size="small " type="success" @click="headUpdate" :disabled="updateDisabled">修改</el-button>
      <el-button plain size="small " type="danger"  @click="headDelete" :disabled="deleteDisabled">删除</el-button>
    </el-form>
    <div id="table">
      <el-table
        :data="userList"
        border
        @selection-change="handleSelectionChange"
        style="width: 100%">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="userId"
          label="用户编号"
          width="180">
      </el-table-column>
      <el-table-column
          prop="userAccount"
          label="用户账号"
          width="180">
      </el-table-column>
      <el-table-column
          prop="userName"
          label="用户昵称">
      </el-table-column>
      <el-table-column
          prop="userEmail"
          label="电子邮箱">
      </el-table-column>
      <el-table-column
          prop="userCodeNum"
          label="代码提交">
      </el-table-column>
      <el-table-column
          prop="userRoleStr"
          label="用户权限">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
        <el-pagination
            background
            layout="total, prev, pager, next"
            :total=total
            :page-size="size"
            :current-page="current"
            @prev-click="previous"
            @next-click="next"
            @current-change="currentChange"
        >
        </el-pagination>
    </div>
    <el-dialog title="编辑用户" :visible.sync="updateUserVisible" center destroy-on-close>
      <update-user-form
          :user-prop="Object.assign({}, this.userProp)"
          :key="new Date().getTime()"
          @exit="exit"
          @reload="reload"></update-user-form>
    </el-dialog>
    <el-dialog title="新增用户" :visible.sync="insertUserVisible" center destroy-on-close>
      <insert-user-form
          @exit="exit"
          @reload="reload"></insert-user-form>
    </el-dialog>
  </div>
</template>

<script>
import UpdateUserForm from '@/components/admin/user-management/update-user-form';
import InsertUserForm from "@/components/InsertUserForm";

export default {
  name: "UserManagement",
  components: {UpdateUserForm, InsertUserForm},
  data() {
    return {
      updateDisabled: true,
      deleteDisabled: true,
      tableSelects: [],
      userList: [],
      total: 0,
      current: 1,
      size: 5,
      updateUserVisible: false,
      insertUserVisible: false,
      allDelete: false,
      user: {
        userAccount: '',
        userEmail: '',
        userName: '',
      },
      userProp: {},
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.tableSelects = val;
      val.length === 1 ? this.updateDisabled = false : this.updateDisabled = true;
      val.length > 0 ? this.deleteDisabled = false : this.deleteDisabled = true;
    },
    headUpdate() {
      if (this.tableSelects.length === 1) {
        this.userProp = this.tableSelects[0];
        this.updateUserVisible = true;
      }
    },
    async headDelete() {
      if (this.tableSelects.length > 0) {
        this.allDelete = true;
        // 使用 for of 是因为 forEach 内部处理异步操作时
        // 则 forEach 同时也处于异步状态，并不会阻塞进程，而是让下面的语句先执行
        for (const value of this.tableSelects) {
          await this.delete(value.userId);
          console.log(this.allDelete);
        }
        this.allDelete = false;
        this.$message({
          message: '删除成功',
          duration: 1000,
          type: 'success'
        });
        await this.load();
      }
    },
    exit() {
      this.updateUserVisible = false;
      this.insertUserVisible = false;
    },
    insert() {
      this.insertUserVisible = true;
    },
    async reload() {
      await this.load();
      this.updateUserVisible = false;
      this.insertUserVisible = false;
    },
    handleEdit(index, row) {
      this.userProp = row;
      this.updateUserVisible = true;
    },
    handleDelete(index, row) {
      this.delete(row.userId);
    },
    async load() {
      await this.$axios({
        method: "get",
        url: `${this.$url}/user/list`,
        params: {
          "userAccount": this.user.userAccount,
          "userEmail": this.user.userEmail,
          "userName": this.user.userName,
          "current": this.current - 1,
        }
      }).then((response) => {
        if (response.data.flag === true) {
          let resp = response.data.data;
          this.userList = resp.records;
          this.userList.forEach((value) => {
            if (value.userId === this.userProp.userId) {
              this.userProp = value;
            }})
          this.total = resp.count - 1;

        }
      })
    },

    next() {
      this.current ++;
      this.load();
    },

    previous() {
      this.current --;
      this.load();
    },

    currentChange(current) {
      this.current = current;
      this.load();
    },

    refresh() {
      this.$refs['userForm'].resetFields();
      console.log(this.user);
      this.load();
    },

    async delete(userId) {

      if (userId === 1) {
        this.$message({
          message: "不能删除管理员",
          type: 'warning',
          duration: 1000
        });
        return;
      }

      await this.$axios({
        method: "post",
        url: `${this.$url}/user/delete`,
        data: {
          "userId": userId,
        }
      }).then((response) => {
        if (response.data.flag === true && this.allDelete === false) {
          this.$message({
            message: '删除成功',
            duration: 1000,
            type: 'success'
          });
          this.load();
        }
      })
    }
  },
  mounted() {
    this.load();
  }
}
</script>

<style scoped>
/*>>>是CSS的伪类, 穿透组件的作用域限制，使得在子组件中可以修改父组件的样式。*/
>>> .el-form-item__label{
  /*设置行高*/
  line-height: 32px;
  /*字体加粗*/
  font-weight: 900;
}
>>> .el-form-item{
  margin-bottom: 0;
}
#button-group{
  margin-bottom: 10px;
  margin-top: 10px;
}
.el-pagination{
  margin-top: 10px;
  float: right;
}
.el-table{
  margin-top: 10px;
}
</style>