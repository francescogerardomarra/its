/*
 * MIT License
 *
 * Copyright (c) 2023 Geekific (https://www.youtube.com/c/Geekific)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice, Geekific's channel link and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.youtube.geekific;

import com.youtube.geekific.factories.AsusManufacturer;
import com.youtube.geekific.factories.MsiManufacturer;
import com.youtube.geekific.factories.Company;
import com.youtube.geekific.products.Monitor;
import com.youtube.geekific.products.Gpu;

public class MainApp {

    /*
     * Video Reference: https://youtu.be/QNpwWkdFvgQ
     */
    public static void main(String[] args) {

        /*
         * use example 1 (NO OUTPUT)
         */

        Company asusManufacturer = new AsusManufacturer();
        Company msiManufacturer = new MsiManufacturer();

        Gpu asusGpu = asusManufacturer.createGpu();
        Monitor asusMonitor = asusManufacturer.createMonitor();
        Gpu msiGpu = msiManufacturer.createGpu();
        Monitor msiMonitor = msiManufacturer.createMonitor();

        /*
         * use example 2 (YES OUTPUT)
         */

        assembleComponents(asusManufacturer);
        assembleComponents(msiManufacturer);

    }

    public static void assembleComponents(Company company) {
        Gpu gpu = company.createGpu();
        Monitor monitor = company.createMonitor();

        System.out.println("Assembling components for Gpu and monitor:");
        gpu.assemble();
        monitor.assemble();
        System.out.println("--------------------------------");
    }

}
