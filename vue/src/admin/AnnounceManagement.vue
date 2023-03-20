<template>
  <el-table
      :data="rows" :row-style="{height: '40px'}"
      style="width: 100%">
    <el-table-column
        label="标题"
        width="180">
      <template slot-scope="scope">
        <div>{{ scope.row.title }}</div>
      </template>
    </el-table-column>
    <el-table-column
        label="内容"
        width="180">
      <template slot-scope="scope">
        <div>{{ scope.row.content }}</div>
      </template>
    </el-table-column>
    <el-table-column
        label="时间">
      <template slot-scope="scope">
        <div>{{ $formatTime(scope.row.datetime) }}</div>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import request from "@/req";

export default {
  name: "AnnounceManagement",
  data() {
    return {
      rows: null,
    }
  },
  methods: {
    handleDelete(index, row) {
      request.post('/admin/deleteArticle', {
        id: this.rows[index].id
      }).then(res => {
        for (let item of this.rows) {
          if (item.id === row.id) {
            this.rows.splice(this.rows.indexOf(item), 1)
          }
        }
        this.$message({
          message: '删除成功',
          type: 'success'
        });
      })
    }
  },
  created() {
    request.get('/forum/getAnnounce').then(res => {
      console.log(res);
      this.rows = res.rows
    })
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
</style>
