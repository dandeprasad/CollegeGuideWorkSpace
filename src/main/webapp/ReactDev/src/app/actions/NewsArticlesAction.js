import React from "react";
import axios from 'axios';
export function newsSubmitReq (datatosend ){

return dispatch =>{


        //        dispatch({
        //     type:"SET_NAME",
        //     payload:"KINGDATA"
        // });


    axios.post('http://dandereddyprasad.us-east-2.elasticbeanstalk.com/NewsStrings', datatosend )
      .then(res => {

const datatopass={};
        for (var i = 0; i < Object.keys(res.data).length; i++) {

  				var clgdata = res.data[i];

var NewsId = clgdata.NEWSID;
datatopass[NewsId]=clgdata;




	}

               dispatch({
            type:"NEWS_ARTICLES_RESULT",
            payload:datatopass
        });

      })



}


}

export function newsSlidesActions (datatosend ){

return dispatch =>{


        //        dispatch({
        //     type:"SET_NAME",
        //     payload:"KINGDATA"
        // });


    axios.post('http://dandereddyprasad.us-east-2.elasticbeanstalk.com/HomeAllNotifications', datatosend )
      .then(res => {

        console.log(res);
        console.log(res.data);
var datatopass=[];
        for (var i = 0; i < Object.keys(res.data).length; i++) {

  				var clgdata = res.data[i];

//var notifyId = clgdata.NOTIFYS_ID;
datatopass[i]=res.data[i];




	}

               dispatch({
            type:"NEWS_SLIDES_RESULT",
            payload:datatopass
        });

      })



}


}
