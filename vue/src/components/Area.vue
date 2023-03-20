<template>
  <div>
    <div class="room">
      <!--      显示底部网格-->
      <div v-for="(item,index) in blankRows" class="item" @click="blankClick(index)">
        <div v-if="!item.show" class="seat"></div>
        <van-popover v-else v-model="item.show" :offset="[0,0]"
                     placement="top" theme="dark">
          <slot name="blankMenu"></slot>
          <template #reference>
            <div class="seat" :class="index===lastBlankIndex?'seatSelect':''"></div>
          </template>
        </van-popover>
      </div>
    </div>
    <div class="room" style="margin-top: -288px">
      <!--      显示座位-->
      <div v-for="(item,index) in seatRows" @click="seatClick(index)"
           class="item" :style="'grid-row:'+item.row+';grid-column:'+ item.column">
        <seat v-if="!item.show" :item="item"></seat>
        <van-popover v-else v-model="item.show" :offset="[0,0]"
                     placement="top" theme="dark">
          <slot name="seatMenu"></slot>
          <template #reference>
            <seat :item="item"></seat>
          </template>
        </van-popover>
      </div>
    </div>
  </div>

</template>

<script>
import Seat from "@/components/Seat";

export default {
  name: "Area",
  components: {Seat},
  data() {
    return {
      blankRows: [],
      lastBlankIndex: -1,
    }
  },
  props: {
    manageMode: {
      type: Boolean,
      default: false
    },
    seatRows: {
      type: Array
    }
  },
  methods: {
    closeLastPop() {
      for (let i = 0; i < this.blankRows.length; i++) {
        this.blankRows[i].show = false
      }
      for (let i = 0; i < this.seatRows.length; i++) {
        this.seatRows[i].show = false
      }
    },
    seatClick(index) {
      this.closeLastPop()
      this.seatRows[index].show = true
      this.$emit('seatClick', index)
    },
    closeOnOutside(ev) {
      if (ev.target.getAttribute('class') === null ||
          ev.target.getAttribute('class').indexOf('seat') === -1) {
        this.closeLastPop()
      }
    },
    blankClick(index) {
      if (!this.manageMode) {
        return
      }
      this.lastBlankIndex = index
      this.closeLastPop()
      this.blankRows[index].show = true

      index += 1;
      let row, column
      if (index % 25 === 0) {
        row = Math.trunc(index / 25)
        column = 25
      } else {
        row = Math.trunc(index / 25) + 1
        column = index % 25
      }
      // console.log(row+'-'+column)
      this.$emit('blankClick', index, row, column)
    }
  },
  created() {
    //定义空白方块数组
    for (let i = 0; i < 225; i++) {
      this.blankRows.push({
        show: false
      })
    }

    window.addEventListener('click', this.closeOnOutside)
  },
  destroyed() {
    window.removeEventListener('click', this.closeOnOutside)
  },
  watch: {
    seatRows(n, o) {
      this.lastBlankIndex = -1
    }
  }
}
</script>

<style scoped>
.room {
  display: grid;
  /*background: #e4e4e4;*/
  grid-template-rows: repeat(9, 32px);
  grid-template-columns: repeat(25, 32px);
}

.item {
  width: 32px;
  height: 32px;
}


</style>
