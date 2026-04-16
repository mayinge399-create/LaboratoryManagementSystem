<template>
	<el-main class="index-main">
		<div class="main-inner">
			<bread-crumbs :title="title" class="bread-crumbs"></bread-crumbs>
			<div class="page-card">
				<router-view class="router-view"></router-view>
			</div>
		</div>
	</el-main>
</template>
<script>
	import menu from "@/utils/menu";
	export default {
		data() {
			return {
				menuList: [],
				role: "",
				currentIndex: -2,
				itemMenu: [],
				title: ''
			};
		},
		mounted() {
			let menus = menu.list();
			this.menuList = menus;
			this.role = this.$storage.get("role");
		},
		methods: {
			menuHandler(menu) {
				this.$router.push({
					name: menu.tableName
				});
				this.title = menu.menu;
			},
			titleChange(index, menus) {
				this.currentIndex = index
				this.itemMenu = menus;
				console.log(menus);
			},
			homeChange(index) {
				this.itemMenu = [];
				this.title = ""
				this.currentIndex = index
				this.$router.push({
					name: 'home'
				});
			},
			centerChange(index) {
				this.itemMenu = [{
					"buttons": ["新增", "查看", "修改", "删除"],
					"menu": "修改密码",
					"tableName": "updatePassword"
				}, {
					"buttons": ["新增", "查看", "修改", "删除"],
					"menu": "个人信息",
					"tableName": "center"
				}];
				this.title = ""
				this.currentIndex = index
				this.$router.push({
					name: 'home'
				});
			}
		}
	};
</script>
<style lang="scss" scoped>
	.index-main {
		min-width: 0;
		padding-top: 10px !important;
	}

	.main-inner {
		width: 100%;
		max-width: 1200px;
		margin: 0 auto;
		min-height: calc(100vh - 60px);
		display: flex;
		flex-direction: column;
	}

	.bread-crumbs {
		width: 100%;
		box-sizing: border-box;
	}

	.page-card {
		margin-top: 12px;
		flex: 1;
		min-height: 0;
		display: flex;
		flex-direction: column;
	}

	.router-view {
		flex: 1;
	}

	@media (max-width: 768px) {
		.main-inner {
			max-width: 100%;
		}
		.page-card {
			margin-top: 10px;
		}
	}
</style>
