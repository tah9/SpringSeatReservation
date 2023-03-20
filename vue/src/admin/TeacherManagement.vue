<template>
  <div>
    <el-form style="padding: 1em" :inline="true" class="demo-form-inline">
      <el-form-item label="学工号">
        <el-input v-model="number" placeholder="学工号"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add">添加</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="rows" :row-style="{height: '40px'}"
        style="width: 100%;">
      <el-table-column
          label="学工号"
          width="180">
        <template slot-scope="scope">
          <div>{{ scope.row.number }}</div>
        </template>
      </el-table-column>
      <el-table-column
          label="姓名"
          width="180">
        <template slot-scope="scope">
          <div>{{ scope.row.username }}</div>
        </template>
      </el-table-column>
      <el-table-column
          label="信用分">
        <template slot-scope="scope">
          <div>{{ scope.row.score }}</div>
        </template>
      </el-table-column>
      <el-table-column
          label="重置密码">
        <template slot-scope="scope">
          <div style="display: flex;align-items: center">
            <input :id="'input'+scope.$index" type="text"></input>
            <el-button
                size="mini"
                @click="remarkPwd(scope.$index, scope.row)">重置
            </el-button>
          </div>

        </template>
      </el-table-column>

    </el-table>
  </div>

</template>

<script>
import request from "@/req";

export default {
  name: "Management",
  data() {
    return {
      rows: null,
      username: null,
      password: null,
      number: null,
    }
  },
  methods: {
    add() {
      request.post('/public/register', {
        number: this.number,
        password: this.password,
        username: this.username,
        type: 1
      }).then(res => {
        this.$message({
          message: '添加成功',
          type: 'success'
        });
        this.number = ''
        this.username = ''
        this.password = ''
        this.update()
      })
    },
    remarkPwd(index, row) {
      this.$nextTick(() => {
        let input = document.getElementById('input' + index);

        request.post('/admin/updatePwd', {
          uid: row.uid,
          password: input.value
        }).then(res => {
          input.value = ''
          this.$message({
            message: '修改成功',
            type: 'success'
          });
        })
      });

    },
    update() {
      request.get('/admin/getTeacher').then(res => {
        console.log(res);
        this.rows = res.rows
      })
    }
  },
  created() {
    this.update()
  }
}
</script>

<style scoped>
div {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}

input {
  border-top: none;
  border-left: none;
  border-right: none;
  border-bottom: #102438 solid 1px;
  width: 10em;
}
</style>
