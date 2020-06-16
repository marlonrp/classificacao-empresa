import { Injectable } from "@angular/core";

import { MonthModel } from "@core/models/month.model";
import { MonthEnum } from "@core/entities/enums/month.enum";

@Injectable({
  providedIn: "root"
})
export class UtilsService {
  public displayName(object: any): string {
    return object && object.name ? object.name : "";
  }

  public filter(name: string, objectList: any): any[] {
    const filterValue = name.toLowerCase();
    return objectList.filter(option =>
      option.name.toLowerCase().includes(filterValue)
    );
  }

  public getMonths(): MonthModel[] {
    let index = 1;
    return Object.keys(MonthEnum)
      .map(key => ({
          name: MonthEnum[key],
          value: index++
      }));
  }
}
