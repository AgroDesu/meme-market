import { Card } from './card';
import { Patron } from './patron';

export class OwnedCard {
    id: number;
    card: Card;
    showcase: number;
    patronId: number;
}
