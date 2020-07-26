import { Component, OnInit } from '@angular/core';
import { HttpServiceService } from '../http-service.service'

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  status: any;
  constructor(protected httpService: HttpServiceService) { }

  ngOnInit(){
    this.httpService.ping().subscribe( this.status )
  }


}
