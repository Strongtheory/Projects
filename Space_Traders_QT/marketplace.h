#ifndef MARKETPLACE_H
#define MARKETPLACE_H

#include <QObject>

class Marketplace : public QObject
{
    Q_OBJECT
public:
    explicit Marketplace(QObject *rootObject = 0, QObject *parent = 0);

    void setRootObject(QObject *rootObject);
    void showMarketplace();

    Q_INVOKABLE void buyItem(int index);
    Q_INVOKABLE void sellItem(int index);

    Q_INVOKABLE void saveChanges();

private:
    QObject* rootObject;

    int creditChanges;
    int quantityChanges;

};

#endif // MARKETPLACE_H
