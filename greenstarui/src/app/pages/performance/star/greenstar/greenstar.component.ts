import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-greenstar',
  templateUrl: './greenstar.component.html',
  styleUrls: ['./greenstar.component.scss'],
})
export class GreenstarComponent implements OnInit {

  @Input()
  public perfStarMonthData: Array<string>;

  private holidayColorCode = "#7beded";
  private complaint100ColorCode = "#7CFC00";
  private complaint75ColorCode = "#FFFF00";
  private complaintBelow75ColorCode = "#FF0000";
  private noDataAvailable = "#FFFFFF";


  public day1FillColor = this.noDataAvailable;
  public day2FillColor = this.noDataAvailable;
  public day3FillColor = this.noDataAvailable;
  public day4FillColor = this.noDataAvailable;
  public day5FillColor = this.noDataAvailable;
  public day6FillColor = this.noDataAvailable;
  public day7FillColor = this.noDataAvailable;
  public day8FillColor = this.noDataAvailable;
  public day9FillColor = this.noDataAvailable;
  public day10FillColor = this.noDataAvailable;
  public day11FillColor = this.noDataAvailable;
  public day12FillColor = this.noDataAvailable;
  public day13FillColor = this.noDataAvailable;
  public day14FillColor = this.noDataAvailable;
  public day15FillColor = this.noDataAvailable;
  public day16FillColor = this.noDataAvailable;
  public day17FillColor = this.noDataAvailable;
  public day18FillColor = this.noDataAvailable;
  public day19FillColor = this.noDataAvailable;
  public day20FillColor = this.noDataAvailable;
  public day21FillColor = this.noDataAvailable;
  public day22FillColor = this.noDataAvailable;
  public day23FillColor = this.noDataAvailable;
  public day24FillColor = this.noDataAvailable;
  public day25FillColor = this.noDataAvailable;
  public day26FillColor = this.noDataAvailable;
  public day27FillColor = this.noDataAvailable;
  public day28FillColor = this.noDataAvailable;
  public day29FillColor = this.noDataAvailable;
  public day30FillColor = this.noDataAvailable;
  public day31FillColor = this.noDataAvailable;
  ngOnInit() {
    this.day1FillColor = this.perfStarMonthData[0];
    this.day2FillColor = this.perfStarMonthData[1];
    this.day3FillColor = this.perfStarMonthData[2];
    this.day4FillColor = this.perfStarMonthData[3];
    this.day5FillColor = this.perfStarMonthData[4];
    this.day6FillColor = this.perfStarMonthData[5];
    this.day7FillColor = this.perfStarMonthData[6];
    this.day8FillColor = this.perfStarMonthData[7];
    this.day9FillColor = this.perfStarMonthData[8];
    this.day10FillColor = this.perfStarMonthData[9];
    this.day11FillColor = this.perfStarMonthData[10];
    this.day12FillColor = this.perfStarMonthData[11];
    this.day13FillColor = this.perfStarMonthData[12];
    this.day14FillColor = this.perfStarMonthData[13];
    this.day15FillColor = this.perfStarMonthData[14];
    this.day16FillColor = this.perfStarMonthData[15];
    this.day17FillColor = this.perfStarMonthData[16];
    this.day18FillColor = this.perfStarMonthData[17];
    this.day19FillColor = this.perfStarMonthData[18];
    this.day20FillColor = this.perfStarMonthData[19];
    this.day21FillColor = this.perfStarMonthData[20];
    this.day22FillColor = this.perfStarMonthData[21];
    this.day23FillColor = this.perfStarMonthData[22];
    this.day24FillColor = this.perfStarMonthData[23];
    this.day25FillColor = this.perfStarMonthData[24];
    this.day26FillColor = this.perfStarMonthData[25];
    this.day27FillColor = this.perfStarMonthData[26];
    this.day28FillColor = this.perfStarMonthData[27];
    this.day29FillColor = this.perfStarMonthData[28];
    this.day30FillColor = this.perfStarMonthData[29];
    this.day31FillColor = this.perfStarMonthData[30];

  }
  constructor() {

  }


}
