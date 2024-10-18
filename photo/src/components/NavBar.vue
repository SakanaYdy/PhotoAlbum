<template>
    <div class="common-layout">
      <el-container>
        <el-header>
          <el-menu
            :default-active="activeIndex2"
            class="el-menu-demo"
            mode="horizontal"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="handleSelect"
            :ellipsis="false"
          >
            <el-menu-item index="1">首页</el-menu-item>
            <el-sub-menu index="2">
              <template #title>相册管理</template>
              <el-menu-item index="2-1">相册类别</el-menu-item>
              <el-menu-item index="2-2">item two</el-menu-item>
              <el-menu-item index="2-3">item three</el-menu-item>
              <el-sub-menu index="2-4">
                <template #title>item four</template>
                <el-menu-item index="2-4-1">item one</el-menu-item>
                <el-menu-item index="2-4-2">item two</el-menu-item>
                <el-menu-item index="2-4-3">item three</el-menu-item>
              </el-sub-menu>
            </el-sub-menu>
            <!-- <el-menu-item index="3" disabled>Info</el-menu-item> -->
            <el-menu-item index="4">个人相册</el-menu-item>
            <el-menu-item index="5" push>
              <img src="https://photo-album-ydy.oss-cn-beijing.aliyuncs.com/avatar/OIP-C.jpg" width="20px"/>
              <div>YDY</div>
            </el-menu-item>
          </el-menu>
        </el-header>
  
        <el-main>
            <el-row :gutter="20">
                <el-col :span="6"><div class="grid-content ep-bg-purple" />
                    <el-card style="max-width: 300px">
                        <template #header>美食</template>
                        <img
                        src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                        style="width: 100%"
                        />
                    </el-card>
                </el-col>
                <el-col :span="6"><div class="grid-content ep-bg-purple" />
                    <el-card style="max-width: 300px">
                        <template #header>风景</template>
                        <img
                        src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                        style="width: 100%"
                        />
                    </el-card>
                </el-col>
                <el-col :span="6"><div class="grid-content ep-bg-purple" />
                    <el-card style="max-width: 300px">
                        <template #header>人像</template>
                        <img
                        src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                        style="width: 100%"
                        />
                    </el-card>
                </el-col>
                <el-col :span="6"><div class="grid-content ep-bg-purple" />
                    <el-card style="max-width: 300px">
                        <template #header>宠物</template>
                        <img
                        src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                        style="width: 100%"
                        />
                    </el-card>
                </el-col>
            </el-row>

        </el-main>
      </el-container>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  export default{
    name: 'NavBar'
  }
  </script>

<style>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
  