import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Room } from '../rooms/room';
import { RoomTerms } from '../rooms/roomTerms';
import { DeleteRoomService } from '../rooms/deleteRoom.servce';
import { RequestForConsult } from '../requestForConsult/requestForConsult';
import { DoctorRequestForConsult } from '../doctorRequestForConsult/doctorRequestForConsult';
import { DoctorRequestForConsultService } from '../doctorRequestForConsult/doctorRequestForConsult.service';
import { ReserveRoomService } from './reserveRooms.service';
import { DoctorTerm } from './doctorTerm';
//import { timingSafeEqual } from 'crypto';
//import { Doctor } from '../doctor/doctor';

@Component({
    templateUrl: './reserveRooms.component.html'
})

export class ReserveRoomsComponent implements OnInit {
    public rooms: Room[];
    public rqs: DoctorRequestForConsult[];
    public roomTerms: RoomTerms[];
    datee : any = new Date().toISOString();
    selRoom : any = new Date().toISOString();
    selRoomReserve : string;
    public docTerms : DoctorTerm[];
    public selDoctor : string;

    
    constructor(private _deleteRoomService: DeleteRoomService,private router: Router,
         private _rqsService : DoctorRequestForConsultService, private rrService: ReserveRoomService) {}


    ngOnInit() { 
        this._rqsService.getRequests().subscribe(
            data=>{
                this.rqs = data;
            }
        )
        this._deleteRoomService.getRoomsEx().subscribe(
        data=>{
            this.rooms = data;
        },
        error=> console.error('Error!', error)
    ) }


    onSubmitDate(idd:string){
        this.rrService.id = idd;
        console.log(idd);
        const mySQLDateString = this.datee.slice(0, 10).replace('T', ' ');
        this.datee = mySQLDateString
        console.log(this.selRoom)
        this._deleteRoomService.getRoomTerms22(this.datee, this.selRoom).subscribe(
            data=>{
                this.roomTerms = data;
                console.log(this.roomTerms);
               // this.router.navigate(['/HomepageCA/allRooms']);
            },
            error=> console.error('Error!', error)
        )
    }

    onSelectTerm(){
        alert(this.selRoomReserve);
        this.rrService.getDoctorTerms(this.datee, this.selRoomReserve).subscribe(
            data=>{
                this.docTerms = data;
                console.log(this.docTerms)
            }
        )
    }

    onSubmitRoom(){
        this.rrService.doctorId = this.selDoctor;
        alert(this.selDoctor)
        console.log(this.selRoomReserve)
        this.rrService.reserveRoom(this.datee,this.selRoomReserve,this.selRoom, this.selDoctor).subscribe(
            data=>{
                console.log(data)
            }
        )
    }
 

}