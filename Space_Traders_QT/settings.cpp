#include "settings.h"

#include <QVariant>

Settings::Settings(QObject *rootObject, QObject *parent) :
    QObject(parent),
    rootObject(rootObject)
{
}

void Settings::setRootObject(QObject *rootObject) {
    this->rootObject = rootObject;
}

void Settings::showSettingsPage() {
    Q_ASSERT(rootObject);
    rootObject->findChild<QObject*>("pageLoader")->setProperty("source", QStringLiteral("qrc:///settings.qml"));
}
