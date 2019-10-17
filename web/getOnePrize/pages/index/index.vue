<template>
	<view class="content">
		<view class="PrizeBgBox">
			<view class="close" @click="closeEvent">
				<img src="./file/close.png" alt="">
			</view>
			<view class="PrizeBox">
				<view class="prize1 prizeItem" :class="prizeIndex && prizeIndex%8==1?'selPrizeItem':''">
					<img src="./file/iphone.png" style="width:62px" alt="">
				</view>
				<view class="prize2 prizeItem" :class="prizeIndex && prizeIndex%8==2?'selPrizeItem':''">
					<img src="./file/10.png" alt="">
				</view>
				<view class="prize3 prizeItem" :class="prizeIndex && prizeIndex%8==3?'selPrizeItem':''">
					<img src="./file/sansung.png" alt="">
				</view>
				<view class="prize8 prizeItem" :class="prizeIndex && prizeIndex%8==0?'selPrizeItem':''">
					<img src="./file/20.png" alt="">
				</view>
				
				<view v-if="goPrizeBut" class="prize9 prizeItem" @click="startEvent">
					<img src="./file/go.png" alt="">
				</view>
				<view v-else class="prize9 prizeItem" @click="goLogin">
				<!-- style="filter: grayscale(100%);" -->
					<img src="./file/go.png" style="filter: grayscale(20%);" alt="">
				</view>
				
				<view class="prize4 prizeItem" :class="prizeIndex && prizeIndex%8==4?'selPrizeItem':''">
					<img src="./file/50.png" alt="">
				</view>
				<view class="prize5 prizeItem" :class="prizeIndex && prizeIndex%8==5?'selPrizeItem':''">
					<img src="./file/thx.png" alt="">
				</view>
				<view class="prize6 prizeItem" :class="prizeIndex && prizeIndex%8==6?'selPrizeItem':''">
					<img src="./file/2.png" alt="">
				</view>
				<view class="prize7 prizeItem" :class="prizeIndex && prizeIndex%8==7?'selPrizeItem':''">
					<img src="./file/lenovo.png" alt="">
				</view>
			</view>
			<view class="text">
				Peserta yang sudah terdaftar memiliki kesempatan 1kali setiap hari nya untuk memenangkan undian keberuntungan.
				Setiap melakukan isi ulang juga bisa mendapatkan kesempatan ekstra. Yuk Coba Keberuntunganmu!

			</view>
		</view>
		<view class="mask" v-show="showPrize" @click="closeEvent">
			<view class="prizeBox" v-if="prizeIt == '0'">
				<view class="prizeBoxTit">
						Sayang sekali !
				</view>
				<view class="prizeBoxTxt">
					<view class="">
						Anda belum beruntung hari ini,
					</view>
					<view class="">
						besok coba lagi ya !
					</view>
				</view>
				<view class="prizeBoxImg">
					<img src="./file/thx.png" style="width: 45%;height: auto;" alt="">
					<view class="butSty" @click="showPrize = false">
						OK
					</view>
				</view>
			</view>
			<view class="prizeBox" v-else>
				<view class="prizeBoxTit">
						Selamat !!
				</view>
				<view class="prizeBoxTxt">
					<view class="">
						Coba terus keberuntungan Anda, 
					</view>
					<view class="">
						hadiah yang lebih besar menanti Anda !
					</view>
				</view>
				<view class="prizeBoxImg">
					<img v-if="prizeIt == '2'" src="./file/2.png" style="width: 28%;" alt="">
					<img v-else-if="prizeIt == '10'" src="./file/10.png" style="width: 28%;" alt="">
					<view class="butSty" @click="showPrize = false">
						OK
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				showPrize: false, //显示抽奖结果 未用
				prizeIt: "0", //抽奖结果 的索引 未用
				userId: this.$route.query.userId,
				// this.$route.query.userId,
				noUser: 'custom',
				// '567293352916025344'
				// this.$route.query.userId,'579590080797081600'
				prizeIndex: null, //奖品的索引
				intTime: 100,
				goPrize: false,
				goPrizeBut:false,//是否可以抽奖 默认不可以
				showPrizeIndex:null
				
			}
		},
		onLoad() {
			console.log('userID', this.userId)
		},
		mounted() {
			this.$nextTick(() => {
				if (this.userId != this.noUser) {
					this.getUserPrize()
				}
			})
		},
		methods: {
			getUserPrize(){
				var v = this
				uni.request({
					url: 'https://www.go-saku.com/api/app/user/award/getAwardRecord', //仅为示例，并非真实接口地址。
					method: 'GET',
					data: {
						userId:v.userId 
					},
					success: function(res){
						if(res.data.message =='0'){
							v.goPrizeBut = true
						}else{
							v.goPrizeBut = false
						}
					}
				})
			},
			getPrize() {
				var v = this
				uni.request({
					url: 'https://www.go-saku.com/api/app/user/award/lotty', //仅为示例，并非真实接口地址。
					method: 'GET',
					data: {
						userId: v.userId
					},
					success: function(res) {
						console.log(res.data);
						if (res.data.message == '2' || res.data.message == '10') {
							v.prizeIt = res.data.message
							if(res.data.message == '2'){
								v.showPrizeIndex = 6
							}else if(res.data.message == '10'){
								v.showPrizeIndex = 2
							}
							setTimeout(()=>{
								v.showPrize = true
							},2000)
							// uni.showModal({
							// 	title: 'Selamat !!',
							// 	content:'Anda memenangkan produk '+res.data.message+'Gcion, coba terus keberuntungan Anda, hadiah yang lebih besar menanti Anda !',
							// 	showCancel: false,
							// 	confirmText: "OK",
							// 	success: function(res) {
							// 		v.closeEvent
							// 	}
							// });
						} else {
							v.prizeIt = 0
							v.showPrizeIndex = 5
							setTimeout(()=>{
								v.showPrize = true
							},2000)
							// uni.showModal({
							// 	title: 'Sayang sekali !',
							// 	content:'Anda belum beruntung hari ini, besok coba lagi ya !',
							// 	showCancel: false,
							// 	confirmText: "OK",
							// 	success: function(res) {
							// 		v.closeEvent
							// 	}
							// });
						}
						v.goPrizeBut = false
						v.stop()//停止抽奖
					}
				});
			},
			startEvent() {
				var v = this
				if (this.userId === this.noUser) {
					// 注册后再来抽奖吧
					uni.showModal({
						title: 'Kembali ke undian berhadiah setelah mendaftar',
						showCancel: false,
						confirmText: "LOGIN",
						success: function(res) {
							v.goLogin()
						}
					});
				} else {
					this.goPrize = true
					this.start()
					setTimeout(() => {
						this.getPrize()
					}, 2.5*1000)
				}
			},
			start() {
				console.log(this.prizeIndex)
				if(this.prizeIndex == 9){
					this.prizeIndex = 0
				}
				this.prizeIndex = this.prizeIndex + 1
				// if (this.goPrize) {
				if (this.prizeIndex !=this.showPrizeIndex) {
					setTimeout(() => {
						this.start()
					}, this.intTime)
				}
			},
			stop(){
				// this.goPrize = false
				
				setTimeout(() => {
					// this.prizeIndex = null
				}, 500)
			},
			closeEvent() {
				$webview.close()
			},
			goLogin(){
				$webview.login()
			}
		}
	}
</script>

<style lang="less">
	.centerSty {
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
	}

	.content {
		height: 100vh;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		.PrizeBgBox {
			background-size: 100%;
			background-repeat: no-repeat;
			background-image: url('./file/bg.png');
			width: 750rpx;
			height: 1094.4rpx;
			position: relative;

			.close {
				width: 69.4rpx;
				height: 69.4rpx;
				border-radius: 100%;
				border: solid 1.3rpx #ff5622;
				position: absolute;
				right: 20.8rpx;

				img {
					.centerSty;
					width: 55.5rpx;
					height: 55.5rpx;
				}
			}

			.PrizeBox {
				// background-color: rgba(0, 0, 0, 0.5);
				// 853 605
				width: 592.3rpx;
				height: 420.1rpx;
				position: absolute;
				top: 304.1rpx;
				left: 50%;
				transform: translateX(-50%);
				text-align: center;

				.prizeItem {
					width: 190.9rpx;
					height: 131.9rpx;
				}

				.selPrizeItem {
					background-color: #ff9b12;
				}

				.prize1 {
					position: absolute;
					left: 0;
					top: 0;

					img {
						.centerSty;
						width: 34.7rpx;
						height: 95.8rpx;
					}
				}

				.prize2 {
					position: absolute;
					left: 50%;
					top: 0;
					transform: translateX(-50%);

					img {
						.centerSty;
						width: 88.8rpx;
						height: 86.1rpx;
					}
				}

				.prize3 {
					position: absolute;
					right: 0;
					top: 0;

					img {
						.centerSty;
						width: 64.5rpx;
						height: 97.2rpx;
					}
				}

				.prize4 {
					position: absolute;
					right: 0;
					top: 50%;
					transform: translateY(-50%);

					img {
						.centerSty;
						width: 88.8rpx;
						height: 86.1rpx;
					}
				}

				.prize5 {
					position: absolute;
					right: 0;
					bottom: 0;

					img {
						.centerSty;
						width: 157.6rpx;
						height: 88.1rpx;
					}
				}

				.prize6 {
					position: absolute;
					left: 50%;
					bottom: 0;
					transform: translateX(-50%);

					img {
						.centerSty;
						width: 88.8rpx;
						height: 86.1rpx;
					}
				}

				.prize7 {
					position: absolute;
					left: 0;
					bottom: 0;

					img {
						.centerSty;
						width: 123.6rpx;
						height: 101.3rpx;
					}
				}

				.prize8 {
					position: absolute;
					left: 0;
					top: 50%;
					transform: translateY(-50%);

					img {
						.centerSty;
						width: 88.8rpx;
						height: 86.1rpx;
					}
				}

				.prize9 {
					position: absolute;
					left: 50%;
					top: 50%;
					transform: translate(-50%, -50%);

					img {
						.centerSty;
						width: 192.3rpx;
						height: 134rpx;
					}
				}
			}
		}

		.text {
			position: absolute;
			left: 50%;
			top: 809rpx;
			transform: translateX(-50%);
			color: #ffffff;
			margin: auto;
			width: 683.3rpx;
			font-size: 26.3rpx;
		}

		.mask {
			position: fixed;
			left: 0;
			top: 0;
			right: 0;
			bottom: 0;
			z-index: 100;
			background-color: rgba(0, 0, 0, 0.7);
			.prizeBox {
				position: absolute;
				left: 50%;
				top: 50%;
				transform: translate(-50%, -50%);
				width: 500rpx;
				height: 560rpx;
				border-radius: 20rpx;
				background: #ff5622;
				.prizeBoxTit{
					font-size: 38.1rpx;
					font-weight: 600;
					text-align: center;
					color: #ffffff;
					padding: 41.6rpx 0 0 0;
				}
				.prizeBoxTxt{
					font-size: 25rpx;
					color: #FFFFFF;
					text-align: center;
					padding-top: 24.3rpx;
				}
				.prizeBoxImg{
					width: 500rpx;
					height: 350rpx;
					background: #FFFFFF;
					border-radius: 20rpx;
					position: absolute;
					left: 0;
					bottom: 0;
					img {
						position: absolute;
						left: 50%;
						transform: translateX(-50%);
						top: 55rpx;
					}
					.butSty{
						width: 375rpx;
						height: 76.3rpx;
						line-height: 76.3rpx;
						border-radius: 20rpx;
						background: #ff5622;
						position: absolute;
						left: 50%;
						transform: translateX(-50%);
						bottom: 20.8rpx;
						color: #FFFFFF;
						text-align: center;
					}
				}
				
			}
		}
	}
</style>
