import React from "react";
import axios from 'axios';
export function eventsoverview (datatosend ){

return dispatch =>{


        //        dispatch({
        //     type:"SET_NAME",
        //     payload:"KINGDATA"
        // });


    axios.post('http://dandereddyprasad.us-east-2.elasticbeanstalk.com/Festsdata', datatosend )
      .then(res => {


const datatopass={};
        for (var i = 0; i < Object.keys(res.data).length; i++) {

  				var clgdata = res.data[i];

var fest_id = clgdata.fest_id;
datatopass[fest_id]=clgdata;




	}

               dispatch({
            type:"EVENTS_DETAILS_OVERVIEW",
            payload:datatopass
        });

      })



}


};
export function eventscalllinks (datatosend ){

return dispatch =>{


        //        dispatch({
        //     type:"SET_NAME",
        //     payload:"KINGDATA"
        // });


    axios.post('http://dandereddyprasad.us-east-2.elasticbeanstalk.com/Festsdata', datatosend )
      .then(res => {


const datatopass={};
        for (var i = 0; i < Object.keys(res.data).length; i++) {

  				var clgdata = res.data[i];

var fest_id = clgdata.fest_id;
datatopass[fest_id]=clgdata;




	}

               dispatch({
            type:"EVENTS_DETAILS_LINKS",
            payload:datatopass
        });

      })



}


};
