//    This file is part of Liquid Wars.
//
//    Copyright (C) 2013-2014 Henry Shepperd (hshepperd@gmail.com)
//
//    Liquid Wars is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Liquid Wars is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Liquid Wars.  If not, see <http://www.gnu.org/licenses/>.

#include "player.hpp"

Player::Player() {
    for(int i = 0; i < 5; i++) {
        x[i] = y[i] = -1;
    }

    score = 0;
}

float Player::getRedf() {
    return (float)((color & 0x00ff0000) >> 16) / (float)0xff;
}

float Player::getGreenf() {
    return (float)((color & 0x0000ff00) >> 8) / (float)0xff;
}

float Player::getBluef() {
    return (float)((color & 0x000000ff) >> 0) / (float)0xff;
}
