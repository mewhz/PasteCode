<template>
  <div id="code-management">
    <el-form inline ref="codeForm" :model="code">
      <el-form-item label="用户账号" prop="userAccount">
        <el-input placeholder="请输入用户账号" v-model="code.userAccount"></el-input>
      </el-form-item>
      <el-form-item label="代码类型" prop="codeType">
        <el-select placeholder="请选择代码类型" v-model="code.codeType" clearable>
          <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="代码标题" prop="codeTitle">
        <el-input placeholder="请输入代码标题" v-model="code.codeTitle"></el-input>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="load">搜索</el-button>
      <el-button icon="el-icon-refresh" @click="refresh">重置</el-button>
      <el-button plain size="small " type="success" @click="headUpdate" :disabled="updateDisabled">修改</el-button>
      <el-button plain size="small " type="danger"  @click="headDelete" :disabled="deleteDisabled">删除</el-button>
    </el-form>
    <div id="table">
      <el-table
          :data="codeList"
          @selection-change="handleSelectionChange"
          style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column type="expand" >
          <template slot-scope="props">
            <pc-code :code="props.row"></pc-code>
          </template>
        </el-table-column>
        <el-table-column
            prop="codeId"
            label="代码编号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="userAccount"
            label="用户账号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="userName"
            label="用户昵称"
            width="180">
        </el-table-column>
        <el-table-column
            prop="codeType"
            label="代码类型">
        </el-table-column>
        <el-table-column
            prop="codeTitle"
            label="代码标题">
        </el-table-column>
        <el-table-column
            prop="codeCreateDate"
            label="提交时间">
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
          @current-change="currentChange"
      >
      </el-pagination>
    </div>


    <el-dialog title="编辑代码" :visible.sync="updateCodeVisible" center destroy-on-close>
      <update-code-form
          :key="new Date().getTime()"
          :code-prop="Object.assign({}, this.codeProp)"
          @exit="exit"
          @reload="reload"></update-code-form>
    </el-dialog>

  </div>
</template>

<script>
import Prism from "prismjs";


import PcCode from "@/components/pc-code";
import UpdateCodeForm from "@/components/admin/codeManagement/UpdateCodeForm";

export default {
  name: "CodeManagement",
  components: {PcCode, UpdateCodeForm},
  data() {
    return {
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
      tableSelects: [],
      deleteDisabled: true,
      updateDisabled: true,
      codeList: [],
      total: 0,
      current: 1,
      size: 3,
      allDelete: false,
      updateCodeVisible: false,
      insertCodeVisible: false,
      codeProp: {},
      code: {
        userAccount: '',
        codeType: '',
        codTitle: '',
      },
      userProp: {},
      userTemp: {}
    }
  },
  methods: {

    headUpdate() {
      if (this.tableSelects.length === 1) {
        this.codeProp = this.tableSelects[0];
        this.updateCodeVisible = true;
      }
    },
    async headDelete() {
      if (this.tableSelects.length > 0) {
        this.allDelete = true;
        // 使用 for of 是因为 forEach 内部处理异步操作时
        // 则 forEach 同时也处于异步状态，并不会阻塞进程，而是让下面的语句先执行
        for (const value of this.tableSelects) {
          await this.delete(value.codeId);
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

    handleSelectionChange(val) {
      this.tableSelects = val;
      val.length === 1 ? this.updateDisabled = false : this.updateDisabled = true;
      val.length  >  0 ? this.deleteDisabled = false : this.deleteDisabled = true;
    },
    insert() {

    },
    exit() {
      this.updateCodeVisible = false;
    },
    async reload() {
      await this.load();
      this.updateCodeVisible = false;
    },
    handleEdit(index, row) {
      // this.userProp = row;
      this.codeProp = row;
      this.updateCodeVisible = true;
      // this.userTemp = Object.assign({}, row);
      // this.updateCodeVisible = true;
      console.log(JSON.stringify(row));
    },
    handleDelete(index, row) {
      // console.log(JSON.stringify(row));
      this.delete(row.codeId);
    },

    async init() {
      await this.load();
    },

    async load(key) {
      if (key != null && key.type === "click") {
        this.current = 1;
      }
      console.log(JSON.stringify(this.code));
      await this.$axios({
        method: "get",
        url: `${this.$url}/code/list`,
        params: {
          "userAccount": this.code.userAccount,
          "codeTitle": this.code.codeTitle,
          "codeType": this.code.codeType,
          "current": this.current - 1,
        }
      }).then((response) => {
        if (response.data.flag === true) {

          let resp = response.data.data;
          this.codeList = resp.records;
          this.total = resp.count;

          this.codeList.forEach((value) => {
            if (value.codeId === this.codeProp.codeId) {
              this.codeProp = value;
            }})
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
      this.$refs['codeForm'].resetFields();
      this.load();
    },

    async delete(codeId) {
      await this.$axios({
        method: "post",
        url: `${this.$url}/code/delete`,
        data: {
          "codeId": codeId,
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
    this.init();
  }
}
</script>

<style scoped>
.el-pagination{
  margin-top: 10px;
  float: right;
}
.el-table{
  margin-top: 10px;
}
</style>