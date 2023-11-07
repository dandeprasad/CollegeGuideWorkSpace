var MiniCssExtractPlugin = require("mini-css-extract-plugin");
var extractPlugin = new MiniCssExtractPlugin({
	filename: 'main.css'
});
var HtmlWebpackPlugin = require("html-webpack-plugin");
 var CleanWebpackPlugin = require("clean-webpack-plugin");
var path = require('path');

module.exports = {
		
		entry : './src/js/app.js',
		output: {
			path: path.resolve(__dirname , 'dist'),
			filename: 'bundle.js'
			
		},
		module:{
			rules: [
			        {
			        	test: /\.js$/,
			        	use:[{
			        		loader:'babel-loader',
			        		 options: {
			        		        presets: ['@babel/preset-env']
			        		      }
			        	}
			        	     
			        	     ]
			        },
				{
					test: /\.scss$/,
					use: [MiniCssExtractPlugin.loader,
					      'css-loader',
				          'sass-loader'
				          ]
					
				},
				{
					test:/\.html$/,
					use:['html-loader']
				},
				{
					test: /\.(jpg|png)$/ ,
					use:
						[
						 {
							 loader:'file-loader',
							 options:{
								 name:'[name].[ext]',
								 outputPath:'img/',
								 publicPath:'img/'
							 }
						 }]
						
					
				}
				
			]
			
		},
		plugins: [
		          extractPlugin,
		         new HtmlWebpackPlugin({
		        	 template : 'src/index.html'
		         }),
		         new CleanWebpackPlugin(['dist'])
		          ]
		//	,
//		plugins: [
//			new webpack.optimize.UglifyJsPlugin({
//				
//			})
//		]
};