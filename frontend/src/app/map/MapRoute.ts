export class Stop {
    x: number = 0;
    y: number = 0;
    address: String = '';
}


export class MapRoute {
    stops: Stop[] = [];

    getAddresses(): String[] {
        let retval: String[] = [];
        this.stops.forEach(s => {
            retval.push(s.address);
        });
        return retval;
    }

    addStop(s: Stop) {
        this.stops.push(s);
    }

}
