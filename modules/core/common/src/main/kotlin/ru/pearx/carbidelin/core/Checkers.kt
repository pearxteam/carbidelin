/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.core


/*
 * Created by mrAppleXZ on 07.09.18.
 */
fun requireRange(min: Int, maxInclusive: Int, element: String, actual: Int)
{
    if(actual < min || actual > maxInclusive)
        throw IllegalArgumentException("The value of $element should be in range $min..$maxInclusive, but was $actual.")
}