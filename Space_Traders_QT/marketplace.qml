import QtQuick 2.2
import QtQuick.Controls 1.1

Image {
    id: image1
    objectName: "marketplaceScreen"
    source: "qrc:///2015-Star-Citizen.jpg"
    fillMode: Image.PreserveAspectCrop
    anchors.fill: parent
    z: -1

    property int creditsAvailable
    property int creditChanges

    Label {
        id: creditsAvailableLabel
        text: qsTr("Credits Available: ")  + creditsAvailable
        color: "orange"
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
    }

    Label {
        id: creditChangesLabel
        text: qsTr("Credit Changes: ")  + creditChanges
        color: "orange"
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8
    }

    Component {
        id: gridItem
        Rectangle {
            width: gridView1.cellWidth;
            height: gridView1.cellHeight
            border.color: "orange"
            border.width: 1
            radius: 5
            color: "transparent"
            Label {
                text: ware
                color: "orange"
                anchors.verticalCenter: parent.verticalCenter
                anchors.left: parent.left
                anchors.leftMargin: 2
                anchors.right: parent.right
                anchors.rightMargin: 2
                horizontalAlignment: Text.AlignHCenter
                wrapMode: Text.Wrap
            }
            MouseArea {
                anchors.fill: parent
                onClicked: {
                    gridView1.currentIndex = index;
                    displayItemInfo.ware = ware;
                    displayItemInfo.buyPrice = buyPrice;
                    displayItemInfo.sellPrice = sellPrice;
                    displayItemInfo.planetQuantity = planetQuantity;
                    displayItemInfo.shipQuantity = shipQuantity;
                }
                onEntered: parent.border.color = "blue"
                onExited: parent.border.color = "orange"
                hoverEnabled: true
            }
        }
    }

    SplitView {
        orientation: Qt.Vertical
        anchors.top: creditsAvailableLabel.bottom
        anchors.topMargin: 6
        anchors.left: parent.left
        anchors.leftMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8
        anchors.bottom: button1.top
        anchors.bottomMargin: 6

        GridView {
            id: gridView1
            height: 150
            model: ListModel {}
            cellHeight: 75
            cellWidth: 75
            delegate: gridItem
            highlight: Rectangle {
                color: "lightsteelblue";
                radius: 5
            }
            focus: true
            clip: true
            currentIndex: -1
        }

        Item {
            id: displayItemInfo

            property string ware
            property int buyPrice
            property int sellPrice
            property int planetQuantity
            property int shipQuantity

            Item {
                id: itemBuyPriceGroup
                anchors.top: parent.top
                anchors.topMargin: 8
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: buyPriceDisplayLabel.height
                implicitWidth: buyPriceLabel.width + buyPriceDisplayLabel.width + 6

                Label {
                    id: buyPriceLabel
                    text: qsTr("Buy Price: ")
                    color: "orange"
                }
                Label {
                    id: buyPriceDisplayLabel
                    anchors.left: buyPriceLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.buyPrice
                    color: "orange"
                }
            }

            Item {
                id: itemSellPriceGroup
                anchors.top: itemBuyPriceGroup.bottom
                anchors.topMargin: 6
                anchors.horizontalCenter: parent.horizontalCenter
                implicitHeight: sellPriceDisplayLabel.height
                implicitWidth: sellPriceLabel.width + sellPriceDisplayLabel.width + 6

                Label {
                    id: sellPriceLabel
                    text: qsTr("Sell Price: ")
                    color: "orange"
                }
                Label {
                    id: sellPriceDisplayLabel
                    anchors.left: sellPriceLabel.right
                    anchors.leftMargin: 6
                    text: displayItemInfo.sellPrice
                    color: "orange"
                }
            }

            Label {
                id: quantityPlanetLabel
                text: qsTr("Quantity on planet: ") + displayItemInfo.planetQuantity
                color: "orange"
                anchors.top: itemSellPriceGroup.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
            }
            Button {
                id: buyItemButton
                text: qsTr("Buy item")
                enabled: displayItemInfo.planetQuantity > 0
                anchors.top: quantityPlanetLabel.bottom
                anchors.topMargin: 6
                anchors.left: parent.left
                anchors.leftMargin: 8
                onClicked: marketplace.buyItem(gridView1.currentIndex)
            }

            Label {
                id: quantityShipLabel
                text: qsTr("Quantity on ship: ") + displayItemInfo.shipQuantity
                color: "orange"
                anchors.top: itemSellPriceGroup.bottom
                anchors.topMargin: 6
                anchors.right: parent.right
                anchors.rightMargin: 8
            }
            Button {
                id: sellItemButton
                text: qsTr("Sell item")
                enabled: displayItemInfo.shipQuantity > 0
                anchors.top: quantityPlanetLabel.bottom
                anchors.topMargin: 6
                anchors.right: parent.right
                anchors.rightMargin: 8
                onClicked: marketplace.sellItem(gridView1.currentIndex)
            }
        }
    }

    Button {
        id: button1
        text: qsTr("Cancel Changes")
        anchors.left: parent.left
        anchors.leftMargin: 8
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        onClicked: mainController.showPlanetNavigationPage()
    }

    Button {
        id: button2
        text: qsTr("Save Changes")
        anchors.right: parent.right
        anchors.rightMargin: 8
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        onClicked: {
            marketplace.saveChanges();
            mainController.saveData();
            mainController.showPlanetNavigationPage();
        }
    }

    DialogBox {
        id: dialogBox
        z: 1
    }

    function createProduct(ware, buyPrice, sellPrice, planetQuantity, shipQuantity) {
        gridView1.model.append({"ware": ware, "buyPrice": buyPrice, "sellPrice": sellPrice,
                                   "planetQuantity": planetQuantity, "shipQuantity": shipQuantity});
    }

    function getProduct(index) {
        return (index < gridView1.model.count) ? gridView1.model.get(index) : 0;
    }

    function setProduct(index, product, updateInfoDisplay) {
        gridView1.model.set(index, product);
        if (updateInfoDisplay) {
            displayItemInfo.ware = product.ware;
            displayItemInfo.buyPrice = product.buyPrice;
            displayItemInfo.sellPrice = product.sellPrice;
            displayItemInfo.planetQuantity = product.planetQuantity;
            displayItemInfo.shipQuantity = product.shipQuantity;
        }
    }

    function showMessage(message) {
        dialogBox.message = message;
        dialogBox.showMessage();
    }
}
