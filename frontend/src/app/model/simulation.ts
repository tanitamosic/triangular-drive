import { Car } from "./car.class";
import 'leaflet';
import 'leaflet-routing-machine';
import { ClientMapComponent } from "../client/client.map/client.map.component";
import { ConstantPool } from "@angular/compiler";

export class Simulation {
}

declare var L: any;

let start_and_end_points:Array<Array<number>> = [
    [45.235866, 19.807387],     // Djordja Mikeša 2
    [45.247309, 19.796717],     // Andje Rankovic 2
    [45.259711, 19.809787],     // Veselina Maslese 62
    [45.261421, 19.823026],     // Jovana Hranilovica 2
    [45.265435, 19.847805],     // Bele njive 24
    [45.255521, 19.845071],     // Njegoseva 2
    [45.249241, 19.852152],     // Stevana Musica 20
    [45.242509, 19.844632],     // Boska Buhe 10A
    [45.254366, 19.861088],     // Strosmajerova 2
    [45.223481, 19.847990]      // Gajeva 2
]


let taxi_stops:Array<Array<number>> = [
    [45.238548, 19.848225],   // Stajaliste na keju
    [45.243097, 19.836284],   // Stajaliste kod limanske pijace
    [45.256863, 19.844129],   // Stajaliste kod trifkovicevog trga
    [45.255055, 19.810161],   // Stajaliste na telepu
    [45.246540, 19.849282]    // Stajaliste kod velike menze
]

export class DriverRideSimulation{
    start:Array<number>=[0,0];
    destination:Array<number>=[0,0];
    position:Array<number>=[0,0];

    constructor(){

    }

    startRide(ride:any){
        this.start=ride.start;
        this.destination=ride.destination;
        this.position = ride.start;
    }

    finish(){

    }

    updatePositions(){
        if(this.destination[0]<this.position[0]){
            this.position[0]=this.position[0]-0.004;
        } else if(Math.abs(this.destination[0]-this.position[0])>0.004){
            this.position[0]=this.position[0]+0.004;
        } else{
            this.position[0]=this.destination[0];
        }

        if(this.destination[1]<this.position[1]){
            this.position[1]=this.position[1]-0.004;
        } else if(Math.abs(this.destination[1]-this.position[1])>0.004){
            this.position[1]=this.position[1]+0.004;
        } else{
            this.position[1]=this.destination[1];
        }
        if(this.destination===this.position){
            this.finish();
        }
    
    }

}


export class AllCarsSimulation{
    allCars:Array<Car> = [];


    constructor(){
        this.initPositions();
    }

    initPositions(){
        this.allCars.forEach(c=>{
            this.newRide(c);
        })
    }

    newRide(c:Car){
        let i = Math.floor(Math.random()*this.allCars.length);
        c.position=start_and_end_points[i];
        
        let j = Math.floor(Math.random()*this.allCars.length);
        while(i===j){
            j = Math.floor(Math.random()*this.allCars.length);
        }
        c.destination = start_and_end_points[j];
    }

    updatePositions(){
        this.allCars.forEach(c=>{
            if(c.destination[0]<c.position[0]){
                c.position[0]=c.position[0]-0.004;
            } else if(Math.abs(c.destination[0]-c.position[0])>0.004){
                c.position[0]=c.position[0]+0.004;
            } else{
                c.position[0]=c.destination[0];
            }

            if(c.destination[1]<c.position[1]){
                c.position[1]=c.position[1]-0.004;
            } else if(Math.abs(c.destination[1]-c.position[1])>0.004){
                c.position[1]=c.position[1]+0.004;
            } else{
                c.position[1]=c.destination[1];
            }
            if(c.destination===c.position){
                this.newRide(c);
            }
        });
    }



}